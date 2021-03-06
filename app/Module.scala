import com.google.inject.AbstractModule
import java.time.Clock

import com.google.inject.name.Names
import play.api.{Environment, Configuration}
import services._
import com.gilt.public.api.{Client => GiltClient}

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)

    val apiKey = configuration.getString("gilt-api-key").get
    bind(classOf[String]).annotatedWith(Names.named("gilt-api-key")).toInstance(apiKey)

    bind(classOf[GiltClient]).toInstance(new GiltClient())

    bind(classOf[UserService]).to(classOf[PostgresUserService])
    bind(classOf[PinService]).to(classOf[PostgresPinService])
  }

}
