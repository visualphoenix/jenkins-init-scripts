// Update system message
def system_message = { instance, html, message ->
  if (html) {
    println "Setting MarkupFormatter to RawHTML"
    instance.setMarkupFormatter(new RawHtmlMarkupFormatter(false))
  }

  println "Setting System Message"
  instance.setSystemMessage(message)
}
