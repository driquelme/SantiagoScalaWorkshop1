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

import cats.effect._
import cats.implicits._
import pureconfig._
import pureconfig.error.ConfigReaderException
import pureconfig.generic.auto._

case class DBConfig(driver: String, url: String, user: String, pass: String)
case class ServerConfig(port: String)

case class Configuration(dbConfig: DBConfig, serverConfig: ServerConfig)

object Configuration {

  def load[F[_]](implicit F: Effect[F]): F[Configuration] =
    F.delay {

        loadConfig[Configuration]

      }
      .flatMap {

        case Left(e) => F.raiseError[Configuration](new ConfigReaderException[Configuration](e))

        case Right(config) => F.pure(config)
      }

}
