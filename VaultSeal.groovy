// interaction functions for working with vaultproject.io
def vault_seal = { vault_token, url, secret ->
  def sout = new StringBuilder(), serr = new StringBuilder()
  def process = ['curl','-H', "X-Vault-Token: ${vault_token}", '-X', 'POST', '-d', "{\"value\":\"${secret}\"}", url].execute()
  process.consumeProcessOutput(sout, serr)
  process.waitForOrKill(1000)
  println serr
  println sout
}
