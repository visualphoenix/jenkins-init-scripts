// Install plugin
def plugin = { instance, plugin_name ->
    def pm = instance.getPluginManager()
    def uc = instance.getUpdateCenter()
    def installed = false
    if (!pm.getPlugin(plugin_name)) {
        def plugin = uc.getPlugin(plugin_name)
        if (plugin) {
            println("Installing $plugin_name")
            plugin.deploy()
            installed = true
        }
        instance.save()
        if (installed) {
            instance.doSafeRestart()
        }
    }
}
