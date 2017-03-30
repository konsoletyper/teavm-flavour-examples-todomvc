package org.teavm.flavour.example.todomvc

import org.teavm.flavour.routing.Routing
import org.teavm.flavour.templates.BindTemplate
import org.teavm.flavour.widgets.ApplicationTemplate
import org.teavm.flavour.widgets.RouteBinder
import org.teavm.jso.dom.html.HTMLDocument
import java.util.function.Consumer

@BindTemplate("templates/todo.html")
class TodoView : ApplicationTemplate(), TodoRoute {
    private val allTodos = mutableListOf<Todo>()
    private var todoFilter: (Todo) -> Boolean = { true }

    val todos: List<Todo> get() = allTodos

    val filteredTodos get() = allTodos.filter(todoFilter)

    var newTodo = ""

    var editedTodo: Todo? = null
        get
        private set

    private var originalTodo: Todo? = null

    var saving = false
        get
        private set

    var filterType = TodoFilterType.ALL
        get
        private set

    val remainingCount get() = todos.count { !it.completed }

    val completedCount get() = todos.count { it.completed }

    val allChecked get() = todos.all { it.completed }

    fun markAll(mark: Boolean) = todos.forEach { it.completed = mark }

    fun addTodo() {
        allTodos += Todo().apply { title = newTodo }
        newTodo = ""
    }

    fun editTodo(todo: Todo) {
        editedTodo = todo
        originalTodo = Todo().apply {
            title = todo.title
            completed = todo.completed
        }
    }

    fun revertEdits(todo: Todo) {
        val backup = originalTodo ?: return
        todo.title = backup.title
        originalTodo = null
        editedTodo = null
    }

    fun saveEdits(todo: Todo, reason: TodoSaveReason) {
        todo.title = editedTodo!!.title.trim()
        originalTodo = null
        editedTodo = null
    }

    fun removeTodo(todo: Todo) {
        allTodos -= todo
    }

    fun clearCompletedTodos() {
        allTodos.removeAll { it.completed }
    }

    override fun unfiltered() {
        todoFilter = { true }
        filterType = TodoFilterType.ALL
    }

    override fun active() {
        todoFilter = { !it.completed }
        filterType = TodoFilterType.ACTIVE
    }

    override fun completed() {
        todoFilter = { it.completed }
        filterType = TodoFilterType.COMPLETED
    }

    fun route(c: Consumer<String>) = Routing.build(TodoRoute::class.java, c)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val view = TodoView()
            RouteBinder()
                .withDefault(TodoRoute::class.java) { it.unfiltered() }
                .add(view)
                .update()
            view.bind(HTMLDocument.current().body)
        }
    }
}
