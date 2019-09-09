/*
 * Copyright 2019 Daniel Riquelme
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package workshop

import cats.effect.{ContextShift, Effect}
import doobie.util.transactor.Transactor
import doobie.implicits._
import cats.implicits._

class DB[F[_]: Effect](implicit config: Configuration, cs: ContextShift[F]) {

  val xa =
    Transactor.fromDriverManager[F](
      config.dbConfig.driver,
      config.dbConfig.url,
      config.dbConfig.user,
      config.dbConfig.pass
    )

  def create =
    sql"""
      CREATE TABLE comment (
        id INT,
        text VARCHAR,
        ts BIGINT
      )
    """.update.run

  val drop =
    sql"""
      DROP TABLE IF EXISTS comment
    """.update.run

  def createDB =
    (drop, create).mapN(_ + _).transact(xa)
}
