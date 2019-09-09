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

import cats.effect.{ContextShift, Effect, ExitCode, Sync, Timer}
import org.http4s.server.blaze.BlazeServerBuilder
import workshop.Main.PORT
import cats.effect._
import cats.implicits._

class Server[F[_]: Sync: Effect](routes: Routes[F])(implicit cs: ContextShift[F], timer: Timer[F], F: ConcurrentEffect[F]) {

  def run(args: List[String]): F[ExitCode] =
    BlazeServerBuilder[F]
      .bindHttp(PORT, "localhost")
      .withHttpApp(routes.router)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
