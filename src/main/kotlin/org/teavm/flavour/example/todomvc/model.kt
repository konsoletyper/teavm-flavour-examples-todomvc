package org.teavm.flavour.example.todomvc

import org.teavm.flavour.json.JsonPersistable

@JsonPersistable
class Todo {
    var title = ""
    var completed = false
}

enum class TodoFilterType {
    ALL,
    ACTIVE,
    COMPLETED
}

interface TodoDataSource {
    fun fetch(): List<Todo>

    fun save(todo: Todo)

    fun delete(todo: Todo)

    fun clearCompleted()
}