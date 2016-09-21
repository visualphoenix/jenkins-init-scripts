// helper to unix chmod utility
def chmod = { perm, file ->
  def sout = new StringBuilder(), serr = new StringBuilder()
  def process = [ 'chmod', perm, file ].execute()
  process.consumeProcessOutput(sout, serr)
  process.waitForOrKill(1000)
}
