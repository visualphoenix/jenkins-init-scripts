// Configure global matrix authorization priviledges
def matrix_authorization = { instance, user_mappings ->
  def new_strategy = instance.getAuthorizationStrategy() instanceof hudson.security.ProjectMatrixAuthorizationStrategy ? new hudson.security.ProjectMatrixAuthorizationStrategy() : new hudson.security.GlobalMatrixAuthorizationStrategy()

  // http://javadoc.jenkins-ci.org/hudson/security/class-use/Permission.html#hudson.slaves
  def known_perms_map = [
     global_admin:                 [ clazz: "jenkins.model.Jenkins", field: "ADMINISTER" ],
     global_read:                  [ clazz: "jenkins.model.Jenkins", field: "READ" ],
     global_run_scripts:           [ clazz: "jenkins.model.Jenkins", field: "RUN_SCRIPTS" ],
     global_upload_plugins:        [ clazz: "hudson.PluginManager",  field: "UPLOAD_PLUGINS" ],
     global_configure_updatecenter:[ clazz: "hudson.PluginManager",  field: "CONFIGURE_UPDATECENTER" ],

     credentials_create:           [ clazz: "com.cloudbees.plugins.credentials.CredentialsProvider", field: "CREATE" ],
     credentials_delete:           [ clazz: "com.cloudbees.plugins.credentials.CredentialsProvider", field: "DELETE" ],
     credentials_manage_domains:   [ clazz: "com.cloudbees.plugins.credentials.CredentialsProvider", field: "MANAGE_DOMAINS" ],
     credentials_update:           [ clazz: "com.cloudbees.plugins.credentials.CredentialsProvider", field: "UPDATE" ],
     credentials_view:             [ clazz: "com.cloudbees.plugins.credentials.CredentialsProvider", field: "VIEW" ],

     agent_build:                  [ clazz: "hudson.model.Computer", field: "BUILD" ],
     agent_configure:              [ clazz: "hudson.model.Computer", field: "CONFIGURE" ],
     agent_connect:                [ clazz: "hudson.model.Computer", field: "CONNECT" ],
     agent_create:                 [ clazz: "hudson.model.Computer", field: "CREATE" ],
     agent_delete:                 [ clazz: "hudson.model.Computer", field: "DELETE" ],
     agent_disconnect:             [ clazz: "hudson.model.Computer", field: "DISCONNECT" ],

     job_build:                    [ clazz: "hudson.model.Item", field: "BUILD" ],
     job_cancel:                   [ clazz: "hudson.model.Item", field: "CANCEL" ],
     job_configure:                [ clazz: "hudson.model.Item", field: "CONFIGURE" ],
     job_create:                   [ clazz: "hudson.model.Item", field: "CREATE" ],
     job_delete:                   [ clazz: "hudson.model.Item", field: "DELETE" ],
     job_discover:                 [ clazz: "hudson.model.Item", field: "DISCOVER" ],
     job_read:                     [ clazz: "hudson.model.Item", field: "READ" ],
     job_workspace:                [ clazz: "hudson.model.Item", field: "WORKSPACE" ],

     run_delete:                   [ clazz: "hudson.model.Run", field: "DELETE" ],
     run_update:                   [ clazz: "hudson.model.Run", field: "UPDATE" ],

     view_configure:               [ clazz: "hudson.model.View", field: "CONFIGURE" ],
     view_create:                  [ clazz: "hudson.model.View", field: "CREATE" ],
     view_delete:                  [ clazz: "hudson.model.View", field: "DELETE" ],
     view_read:                    [ clazz: "hudson.model.View", field: "READ" ],

     scm_tag:                      [ clazz: "hudson.scm", field: "SCM.TAG"],
     metrics_health_check:         [ clazz: "jenkins.metrics.api.Metrics", field: "HEALTH_CHECK" ],
     metrics_thread_dump:          [ clazz: "jenkins.metrics.api.Metrics", field: "THREAD_DUMP" ],
     metrics_view:		             [ clazz: "jenkins.metrics.api.Metrics", field: "VIEW" ],

     job_extendedread:             [ clazz: "hudson.model.Item", field: "EXTENDED_READ" ],
     job_move:                     [ clazz: "com.cloudbees.hudson.plugins.folder.relocate.RelocationAction", field: "RELOCATE"],

     view_replay:                  [ clazz: "org.jenkinsci.plugins.workflow.cps.replay.ReplayAction", field: "REPLAY"],
  ]

  // create a map of valid permissions depending on what plugins/classes are available
  def valid_perms_map = [:]
  known_perms_map.each { key, val ->
    try {
      def clazz = Class.forName(val['clazz'], false, this.getClass().getClassLoader())
      def field = clazz.getField(val['field'])
      if(field) {
        valid_perms_map.put(key,field.get(clazz))
      }
    } catch (Exception e) {
    } finally {
    }
  }

  def heading = []
  def user_vals = [:]
  user_mappings.eachLine { line, count ->
    if(count<1) {
      heading = line.split(/,/)
    } else {
      values = line.split(/,/)
      pairs = [heading, values].transpose()
      map = [:]
      pairs.each{ k, v -> map[k] = v }
      user = map['name']
      map.remove('name')
      user_vals[user] = map
    }
  }
  user_vals.each { user, map ->
      map.each { k, v ->
        if(v.toBoolean()) {
          if(valid_perms_map.containsKey(k)) {
            new_strategy.add(valid_perms_map[k], user)
          }
        }
      }
  }

  instance.setAuthorizationStrategy(new_strategy)
  println "Setting matrix authentication"
}
