package org.teavm.flavour.example.todomvc

import org.teavm.flavour.templates.*
import org.teavm.jso.dom.events.EventListener
import org.teavm.jso.dom.events.KeyboardEvent

@BindAttributeComponent(name = arrayOf("escape"))
class EscapeComponent(private val target: ModifierTarget) : Renderable {
    @set:BindContent
    var action: Runnable = Runnable {}

    override fun render() {
        target.element.addEventListener("keydown", eventListener)
    }

    override fun destroy() {
        target.element.removeEventListener("keydown", eventListener)
    }

    private val eventListener = EventListener<KeyboardEvent> {
        if (it.keyCode == ESCAPE_KEY) {
            action.run()
        }
    }

    companion object {
        val ESCAPE_KEY = 27
    }
}