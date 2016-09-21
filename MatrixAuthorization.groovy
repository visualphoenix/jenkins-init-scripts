// Configure global matrix authorization priviledges
def matrix_authorization = { instance, user_mappings ->
  def new_strategy = instance.getAuthorizationStrategy() instanceof hudson.security.ProjectMatrixAuthorizationStrategy ? new hudson.security.ProjectMatrixAuthorizationStrategy() : new hudson.security.GlobalMatrixAuthorizationStrategy()

  // http://javadoc.jenkins-ci.org/hudson/security/class-use/Permission.html#hudson.slaves
  def valid_perms_map = [
     global_admin:                 jenkins.model.Jenkins.ADMINISTER,
     global_configure_updatecenter:hudson.PluginManager.CONFIGURE_UPDATECENTER,
     global_read:                  jenkins.model.Jenkins.READ,
     global_run_scripts:           jenkins.model.Jenkins.RUN_SCRIPTS,
     global_upload_plugins:        hudson.PluginManager.UPLOAD_PLUGINS,

     credentials_create:           com.cloudbees.plugins.credentials.CredentialsProvider.CREATE,
     credentials_delete:           com.cloudbees.plugins.credentials.CredentialsProvider.DELETE,
     credentials_manage_domains:   com.cloudbees.plugins.credentials.CredentialsProvider.MANAGE_DOMAINS,
     credentials_update:           com.cloudbees.plugins.credentials.CredentialsProvider.UPDATE,
     credentials_view:             com.cloudbees.plugins.credentials.CredentialsProvider.VIEW,

     agent_build:                  hudson.model.Computer.BUILD,
     agent_configure:              hudson.model.Computer.CONFIGURE,
     agent_connect:                hudson.model.Computer.CONNECT,
     agent_create:                 hudson.model.Computer.CREATE,
     agent_delete:                 hudson.model.Computer.DELETE,
     agent_disconnect:             hudson.model.Computer.DISCONNECT,

     job_build:                    hudson.model.Item.BUILD,
     job_cancel:                   hudson.model.Item.CANCEL,
     job_configure:                hudson.model.Item.CONFIGURE,
     job_create:                   hudson.model.Item.CREATE,
     job_delete:                   hudson.model.Item.DELETE,
     job_discover:                 hudson.model.Item.DISCOVER,
     job_read:                     hudson.model.Item.READ,
     job_workspace:                hudson.model.Item.WORKSPACE,

     run_delete:                   hudson.model.Run.DELETE,
     run_update:                   hudson.model.Run.UPDATE,

     view_configure:               hudson.model.View.CONFIGURE,
     view_create:                  hudson.model.View.CREATE,
     view_delete:                  hudson.model.View.DELETE,
     view_read:                    hudson.model.View.READ,

     scm_tag:                      hudson.scm.SCM.TAG,
     metrics_health_check:         jenkins.metrics.api.Metrics.HEALTH_CHECK,
     metrics_thread_dump:          jenkins.metrics.api.Metrics.THREAD_DUMP,
     metrics_view:		             jenkins.metrics.api.Metrics.VIEW,

     job_extendedread:             hudson.model.Item.EXTENDED_READ,
     job_move:                     com.cloudbees.hudson.plugins.folder.relocate.RelocationAction.RELOCATE,

     view_replay:                  org.jenkinsci.plugins.workflow.cps.replay.ReplayAction.REPLAY,
  ]

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
