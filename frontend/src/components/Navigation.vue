<template>
  <div>
    <nav>
      <router-link class="spacing"
                   v-model="msg"
                   v-for="routes in links"
                   v-bind:key="routes.id"
                   :to=routes.page>{{routes.text}}</router-link>
    </nav>
  </div>
</template>

<script>

export default {
  name: 'Navigation',
  data () {
    return {
      links: [
        {
          id: 0,
          text: 'Główna',
          page: '/'
        },
        {
          id: 1,
          text: 'Pytania',
          page: '/questions'
        },
        {
          id: 2,
          text: 'Testy',
          page: '/tests'
        },
        {
          id: 3,
          text: 'Profil',
          page: '/profile'
        }
      ]
    }
  }
}

</script>

<style scoped>
.spacing{
  Margin-right: 10px
}
</style>
