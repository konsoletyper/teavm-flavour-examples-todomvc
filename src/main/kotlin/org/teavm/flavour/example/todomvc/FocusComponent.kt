package org.teavm.flavour.example.todomvc

import org.teavm.flavour.templates.BindAttributeComponent
import org.teavm.flavour.templates.BindContent
import org.teavm.flavour.templates.ModifierTarget
import org.teavm.flavour.templates.Renderable

@BindAttributeComponent(name = arrayOf("focus"))
class FocusComponent(private val target: ModifierTarget) : Renderable {
    @set:BindContent
    var isFocused: () -> Boolean = { false }

    override fun render() {
        if (isFocused()) {
            target.element.focus()
        }
    }

    override fun destroy() { }
}