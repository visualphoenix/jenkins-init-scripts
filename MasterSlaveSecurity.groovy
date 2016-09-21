// Configure global master-slave security
def master_slave_security = { instance, home, disabled=true ->
  s = new File(home + '/secrets/filepath-filters.d').mkdirs()
  s = new File(home + '/secrets/filepath-filters.d/50-gui.conf').createNewFile()
  s = new File(home + '/secrets/whitelisted-callables.d').mkdirs()
  s = new File(home + '/secrets/whitelisted-callables.d/gui.conf').createNewFile()
  instance.getInjector().getInstance(jenkins.security.s2m.AdminWhitelistRule.class).setMasterKillSwitch(disabled)
  println 'Enabled Master -> Slave Security'
}
