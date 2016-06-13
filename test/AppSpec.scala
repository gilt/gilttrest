
import com.gilt.gilt.trest.v1.models.User
import com.gilt.public.api.{SaleList => SaleL}
import com.gilt.public.api.models.SaleList
import com.gilt.public.api.models.json._
import com.gilt.public.api.{Client => GiltClient}
import org.joda.time.DateTime
import org.mockito.Matchers
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{TestData, BeforeAndAfter}
import org.scalatestplus.play._
import org.scalatest.concurrent.ScalaFutures
import play.api.libs.json.Json
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.AnyContentAsEmpty
import play.api.test._
import play.api.test.Helpers._
import services.{PostgresPinService, PostgresUserService}
import sun.misc.BASE64Encoder
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.inject.bind

import scala.concurrent.Future

class AppSpec extends PlaySpec with OneAppPerTest  with BeforeAndAfter with ScalaFutures with MockitoSugar {


  val injector = new GuiceApplicationBuilder().build().injector
  val userService = injector.instanceOf[PostgresUserService]
  val pinService = injector.instanceOf[PostgresPinService]
  var user: User = _

  val mockJson = Json.parse("""{ "sales":
                                 [
                                   {
                                     "name": "Derek Lam 10 Crosby",
                                     "sale": "https://api.gilt.com/v1/sales/women/10-crosby-derek-lam-6510/detail.json",
                                     "sale_key": "10-crosby-derek-lam-6510",
                                     "store": "women",
                                     "sale_url": "http://www.gilt.com/sale/women/10-crosby-derek-lam-6510?utm_medium=api&utm_source=salesapi",
                                     "begins": "2016-06-12T21:00:00.000-04:00",
                                     "image_urls": {},
                                     "description": "Nail the downtown look with sleek, city-infused finds",
                                     "ends": "2016-06-15T21:00:00.000-04:00",
                                     "products": []
                                   }
                                 ]
                               }""")

  val mockClient = mock[GiltClient]
  val mockSaleList = mock[SaleL]
  when(mockClient.saleList).thenReturn(mockSaleList)
  when(mockSaleList.getActiveJson(Matchers.anyString(), Matchers.any())(Matchers.any())).thenReturn(Future.successful(Json.fromJson[SaleList](mockJson).get))
  when(mockSaleList.getActiveJsonByStore(Matchers.eq("men"), Matchers.anyString(), Matchers.any())(Matchers.any())).thenReturn(Future.successful(Json.fromJson[SaleList](mockJson).get))

  override def newAppForTest(testData: TestData): Application = new GuiceApplicationBuilder()
    .overrides(bind(classOf[GiltClient]).to(mockClient))
    .build()

  before {
    user = userService.create(name="testy", username="testyMcTestFace", password="test123").futureValue
  }

  after {
    userService.delete(user.id)
  }


  val authorizationHeader = List("Authorization" -> s"Basic ${new BASE64Encoder().encode("testyMcTestFace:test123".getBytes)}")

  "Pins" should {

    "returns Unauthorized on missing authorization" in {
      val result = route(app, FakeRequest(PUT, "/api/pins/foo")).get
      status(result) mustBe UNAUTHORIZED
    }

    "returns error on invalid sale name" in {
      val authorizationHeader = List("Authorization" -> s"Basic ${new BASE64Encoder().encode("testyMcTestFace:test123".getBytes)}")
      val result = route(app, FakeRequest(PUT, "/api/pins/foo", FakeHeaders(authorizationHeader), AnyContentAsEmpty)).get
      status(result) mustBe BAD_REQUEST
    }

    "returns success on valid sale name" in {
      val authorizationHeader = List("Authorization" -> s"Basic ${new BASE64Encoder().encode("testyMcTestFace:test123".getBytes)}")
      val result = route(app, FakeRequest(PUT, "/api/pins/10-crosby-derek-lam-6510", FakeHeaders(authorizationHeader), AnyContentAsEmpty)).get
      status(result) mustBe OK
      pinService.find(user).futureValue.head.saleKey mustBe "10-crosby-derek-lam-6510"
    }

  }

  "Stores" should {
    "returns Unauthorized on missing authorization" in {
      val result = route(app, FakeRequest(GET, "/api/stores/men")).get
      status(result) mustBe UNAUTHORIZED
    }

    "returns error on invalid store name" in {
      val result = route(app, FakeRequest(GET, "/api/stores/foo", FakeHeaders(authorizationHeader), AnyContentAsEmpty)).get
      status(result) mustBe BAD_REQUEST
    }

    "returns success on valid store name"in {
      val result = route(app, FakeRequest(GET, "/api/stores/men", FakeHeaders(authorizationHeader), AnyContentAsEmpty)).get
      status(result) mustBe OK
    }

    "returns true when a sale is pinned" in {
      pinService.upsert(user, "10-crosby-derek-lam-6510", DateTime.now()).futureValue
      val result = route(app, FakeRequest(GET, "/api/stores/men", FakeHeaders(authorizationHeader), AnyContentAsEmpty)).get
      contentAsString(result) contains "true"
    }

    "returns false when a sale is not pinned" in {
      pinService.delete(user, "10-crosby-derek-lam-6510").futureValue
      val result = route(app, FakeRequest(GET, "/api/stores/men", FakeHeaders(authorizationHeader), AnyContentAsEmpty)).get
      contentAsString(result) contains "false"
    }
  }


}
