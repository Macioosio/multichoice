<template>
  <div>
    <h2 v-html="polonizeRouteName($route.name)"></h2>
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
function polonizeRouteName (routeName) {
  switch (routeName) {
    case 'Questions': return 'Pytania'
    case 'Tests': return 'Testy'
    case 'Home': return 'Strona główna'
    case 'Profile': return 'Profil'
    default: return 'Unknown'
  }
}

export default {
  name: 'Navigation',
  methods: {
    polonizeRouteName
  },
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
