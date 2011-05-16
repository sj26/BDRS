# Biological Data Recording System

This is the [Atlas of Living Australia][ala] Citizen Science project codebase.

## Getting Started

 1. Make sure you have a [Java SDK][javaee], [Maven][maven], and [PostgreSQL][postgresql] with [PostGIS 1.5][postgis] all installed. Check out Dependencies below if you're not sure how.
 
 2. Generate the database definitions:
    
        mvn -P dev -D skipTests clean package hibernate3:hbm2ddl
    
    __This can take a while__ as it downloads all the project dependencies for the first time. Don't worry, they'll be cached. We skip tests as there's no database set up yet.
    
 3. If you haven't already, create a template PostGIS database:
    
        createdb template_postgis
        createlang plpgsql template_postgis
        psql -d template_postgis -f /usr/share/postgresql/8.4/contrib/postgis-1.5/postgis.sql
        psql -d template_postgis -f /usr/share/postgresql/8.4/contrib/postgis-1.5/spatial_ref_sys.sql
    
    Adjust the locations to PostGIS as necessary. On Mac, installed via homebrew, the PostGIS SQL scripts should be in `/usr/local/share/postgis/`.
    
 4. Create the project database:
 
    If you haven't already, create a database user:
    
        createuser <user>
    
    Then create the database for this user based on the PostGIS template:
    
        createdb <database> -O <user> -T template_postgis
    
    And import the schema we just generated:
    
        psql -U <user> <database> 
    
 5. Configure your database:
    
    Open `pom.xml` and edit the "Database properties" near the bottom in the dev profile, or copy and paste a new profile and use maven with `-P <profile>`:
      
        <!-- Database properties -->
        <bdrs.db.user.name>bdrs</bdrs.db.user.name>
        <bdrs.db.user.password>password</bdrs.db.user.password>
        <bdrs.db.url>jdbc:postgresql://localhost:5432/bdrs</bdrs.db.url>
        <bdrs.db.driver>org.postgresql.Driver</bdrs.db.driver>
    
    Also open `src/main/webapp/WEB-INF/climatewatch-hibernate-datasource.xml` and place the same settings in the `dataSource` bean:
    
        <property name="driverClassName" value="org.postgresql.Driver"/>
        <property name="url" value="jdbc:postgresql://localhost:5432/bdrs-dev"/>
        <property name="username" value="develop"/>
        <property name="password" value="develop"/>
    
 6. Run it:
    
        mvn jetty:run
    
 7. Check it out: open _http://localhost:8080/BDRS_ and login with username _admin_ and password _password_.

## Deployment

The BDRS is packaged up as a standard WAR in `target/bdrs-core.war`. Deploy this via your application server of choice!

## Dependencies

Contributed dependency installation information:

### Mac OS X:

  * Java SDK: http://www.apple.com/downloads/macosx/development_tools/javaee5sdk.html
  * Using [Homebrew][homebrew]:
    * Maven: `brew install maven`
    * PostgreSQL: `brew install postgresql`
    * PostGIS: `brew install postgis`

## License

Released under the [Mozilla Public License 1.0][mpl] by [Gaia Resources][gaia].

  [ala]: http://ala.org.au/
  [gaia]: http://www.gaiaresources.com.au/
  [homebrew]: http://github.com/mxcl/homebrew
  [javaee]: http://www.oracle.com/technetwork/java/javaee/
  [maven]: http://maven.apache.org/
  [mpl]: http://www.mozilla.org/MPL/
  [postgresql]: http://www.postgresql.org/
  [postgis]: http://postgis.refractions.net/
    