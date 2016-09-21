// interaction functions for working with vaultproject.io
def vault_unseal = { vault_token, url, out_file ->
  def sout = new StringBuilder(), serr = new StringBuilder()
  def process = ['curl','-H', "X-Vault-Token: ${vault_token}", '-X', 'GET', url].execute()
  process.consumeProcessOutput(sout, serr)
  process.waitForOrKill(1000)
  println serr
  def j = new groovy.json.JsonSlurper().parseText(sout.toString().trim())
  def val = j.data ? j.data.value : j.value
  if(out_file != '-') {
    def file = new File(out_file)
    def dir = new File(file.getParent()).mkdirs()
    file.createNewFile()
    file << val
  }
  val
}
