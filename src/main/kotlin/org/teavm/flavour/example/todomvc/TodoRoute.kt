package org.teavm.flavour.example.todomvc

import org.teavm.flavour.routing.Path
import org.teavm.flavour.routing.PathSet
import org.teavm.flavour.routing.Route

@PathSet
interface TodoRoute : Route {
    @Path("/")
    fun unfiltered()

    @Path("/active")
    fun active()

    @Path("/completed")
    fun completed()
}

