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

import java.util.concurrent.Executors

import cats.effect._
import scala.concurrent.ExecutionContext


object Main extends IOApp {

  val blockingEc = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(4))

  val PORT = 8080

  // Attempts to load a configuration file
  implicit val configuration = Configuration.load[IO].unsafeRunSync()
  val db = new DB[IO]

  db.createDB.unsafeRunSync()

  val repository = new RepositoryDBImpl[IO](db.xa)

  val routes = new Routes(repository, blockingEc)
  val server = new Server(routes)

  // IOApp entry point
  def run(args: List[String]) =
    server.run(args)

}
