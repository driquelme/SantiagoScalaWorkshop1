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

import org.scalatest.{FunSuite, Matchers}
import io.circe._
import io.circe.syntax._
import cats.effect._
import org.http4s._
import org.http4s.circe._
import org.http4s.implicits._
import TestCommon._
import doobie.util.ExecutionContexts

import scala.concurrent.ExecutionContext
import scala.language.reflectiveCalls

class RoutesTest extends FunSuite with Matchers {

  import CirceCodecs._

  implicit val ec = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val routes = new Routes[IO](dummyRepository, ec).router

  test("GET comments should return comments in dummyRepository") {

    val expectedJson = Json.arr(Json.obj("id" := 1, "text" := "First Comment", "timestamp" := 0L))

    val response: IO[Response[IO]] = routes
      .run(
        Request(method = Method.GET, uri = uri"/comments")
      )
    assert(check[Json](response, Status.Ok, Some(expectedJson)))
  }

  test("POST comments should add a comment to dummyRepository") {

    val response: IO[Response[IO]] = routes
      .run(
        Request(method = Method.POST, uri = uri"/comments")
          .withEntity(Comment(2, "Second Comment", 1).asJson)
      )

    assert(check[String](response, Status.Ok, Some("Comment added")))

    dummyRepository.db.size shouldBe 2
  }

}
