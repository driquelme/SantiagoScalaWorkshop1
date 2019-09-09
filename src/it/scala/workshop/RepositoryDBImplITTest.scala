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

import cats.effect.IO
import org.scalatest.{FunSuite, Matchers}

class RepositoryDBImplITTest extends FunSuite with Matchers {

  import doobie.util.ExecutionContexts

  implicit val configuration = Configuration.load[IO].unsafeRunSync()
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
  val db = new DB[IO]
  implicit val xa = db.xa

  db.createDB.unsafeRunSync()

  val repository = new RepositoryDBImpl[IO](db.xa)

  test("Repository should be empty after creating the db") {
    val comments = repository.listComments.unsafeRunSync()
    comments shouldBe List.empty[Comment]
  }

  test("Repository should have a single item when we one") {

    val comment = Comment(1, "First Comment", 0L)

    repository.putComment(comment).unsafeRunSync() shouldBe 1

    val comments = repository.listComments.unsafeRunSync()
    comments shouldBe List(comment)
  }
}
