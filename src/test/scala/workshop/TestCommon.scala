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
import org.http4s._

import scala.collection.mutable

object TestCommon {
  def check[A](actual: IO[Response[IO]], expectedStatus: Status, expectedBody: Option[A])(
    implicit ev: EntityDecoder[IO, A]
  ): Boolean = {
    val actualResp  = actual.unsafeRunSync
    val statusCheck = actualResp.status == expectedStatus
    val bodyCheck = expectedBody.fold[Boolean](
      actualResp.body.compile.toVector.unsafeRunSync.isEmpty
    )( // Verify Response's body is empty.
      expected => {
        println(actualResp.as[A].unsafeRunSync)
        actualResp.as[A].unsafeRunSync == expected
      }
    )
    statusCheck && bodyCheck
  }

  implicit val dummyRepository = new Repository[IO] {

    val db = mutable.Buffer.empty[Comment]

    db addOne Comment(1, "First Comment", 0L)

    override def listComments = IO(db.toList)

    override def putComment(comment: Comment): IO[Int] = IO {
      db addOne comment
      1
    }
  }
}
