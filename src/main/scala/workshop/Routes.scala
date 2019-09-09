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
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.headers._
import org.http4s.implicits._
import org.http4s.server.Router

import scala.concurrent.ExecutionContext

class Routes[F[_]: Effect](repository: Repository[F], ec: ExecutionContext)(
  implicit cs: ContextShift[F]
) extends Http4sDsl[F] {

  import CirceCodecs._
  implicit val decoder = jsonOf[F, Comment]
  val blockerEC        = Blocker.liftExecutionContext(ec)

  val routes = HttpRoutes.of[F] {

    case GET -> Root => ??

    case GET -> Root / "comments" => ??

    case req @ POST -> Root / "comments" => ??
  }

  val router = Router("/" -> routes).orNotFound

}
