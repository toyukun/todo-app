<!-- Please remove this file from your project -->
<template>
  <section class="text-gray-600 body-font">
    <div class="container px-5 py-24 mx-auto flex flex-wrap">
      <div class="flex flex-wrap w-full">
        <div class="lg:w-2/5 md:w-1/2 md:pr-10 md:py-6">
          <div v-for="task in tasks" class="flex relative pb-12" :key="task.id">
            <div class="h-full w-10 absolute inset-0 flex items-center justify-center">
              <div class="h-full w-1 bg-gray-200 pointer-events-none"></div>
            </div>
            <div @click="deleteTask(task.id)"
                 class="flex-shrink-0 w-10 h-10 rounded-full bg-indigo-500 inline-flex items-center justify-center text-white relative z-10">
              <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                   class="w-5 h-5" viewBox="0 0 24 24">
                <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
              </svg>
            </div>
            <div class="flex-grow pl-4">
              <h2 class="font-medium title-font text-sm text-gray-900 mb-1 tracking-wider">{{task.title}}</h2>
              <p class="leading-relaxed">
                {{task.description}}
              </p>
            </div>
          </div>
        </div>
        <div class="lg:w-2/5 md:w-1/2 md:pr-10 md:py-6">
          <form>
            <div class="mb-6">
              <label for="title" class="text-sm font-medium text-gray-900 block mb-2">Title</label>
              <input
                id="title"
                v-model="form.title"
                type="text"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
              >
            </div>
            <div class="mb-6">
              <label for="description" class="text-sm font-medium text-gray-900 block mb-2">Description</label>
              <textarea
                id="description"
                v-model="form.description"
                rows="5"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
              ></textarea>
            </div>
            <button
              class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
              @click="createTask"
            >
              TODO追加
            </button>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>
<script>

export default {
  data () {
    return {
      form: {
        title: null,
        description: null
      },
      tasks: []
    }
  },
  mounted () {
    this.getTasks()
  },
  methods: {
    async getTasks () {
      this.tasks = await this.$axios.$get('http://localhost:8080/tasks')
    },
    async createTask () {
      await this.$axios.$post('http://localhost:8080/tasks', this.form)
      await this.getTasks()
    },
    async deleteTask (taskId) {
      await this.$axios.$delete(`http://localhost:8080/tasks/${taskId}`)
      await this.getTasks()
    }
  }
}
</script>
