workspace "Social Media Platform" "Social Media Video System" {

    model {
      user = person "User"
      system = softwareSystem "Social Media Video System" {
          cli = container "Video CLI Client"
          
          TemplateMicroservice = container "Microservice" {
            Domain = component "Domain objects and DTOs"
            Repos = component "Repositories"
            Events = component "Kafka consumers and producers"
            Controllers = component "Controllers"
          }
          Database = container "Database" "" "MariaDB" "database"

          VM = container "Video Microservice" {
            VmDomain = component "Domain objects and DTOs"
            VmRepos = component "Repositories"
            VmEvents = component "Kafka consumers and producers"
            VmControllers = component "Controllers"
          }
          VmDatabase = container "VM Database" "" "MariaDB" "database"
          
          
          THM = container "Trending Hashtag Microservice" {
            ThmDomain = component "Domain objects and DTOs"
            ThmRepos = component "Repositories"
            ThmEvents = component "Kafka consumers and producers"
            ThmResources = component "Controllers"
          }
          ThmDatabase = container "THM Database" "" "MariaDB" "database"
          
          SM = container "Subscription Microservice" {
            SmDomain = component "Domain objects and DTOs"
            SmRepos = component "Repositories"
            SmEvents = component "Kafka consumers and producers"
            SmResources = component "Controllers"
          }
          SmDatabase = container "SM Database" "" "MariaDB" "database"
          
          kafka = container "Kafka Cluster"
      }


      user -> cli "Uses"

      cli -> VM "Interacts with HTTP API"
      cli -> THM "Interacts with HTTP API"
      cli -> SM "Interacts with HTTP API"

      VM -> VmDatabase "Reads from and writes to"
      THM -> ThmDatabase "Reads from and writes to"
      SM -> SmDatabase "Reads from and writes to"
      
      VM -> kafka "Consumes and produces events"
      THM -> kafka "Consumes and produces events"
      SM -> kafka "Consumes and produces events"

      Repos -> Domain "Creates and updates"
      Repos -> Database "Queries and writes to"
      Controllers -> Repos "Uses"
      Controllers -> Events "Uses"
      Controllers -> Domain "Reads and updates"
      cli -> Controllers "Invokes"
      Events -> kafka "Consumes and produces events in"
    }

    views {
        theme default
        systemContext system {
            include *
        }
        container system {
            include user
            include cli
            include VM
            include VmDatabase
            include THM
            include ThmDatabase
            include SM
            include SmDatabase
            include kafka
        }
        component TemplateMicroservice {
            include *
        }
        styles {
            element "database" {
              shape Cylinder
            }
        }
    }

}