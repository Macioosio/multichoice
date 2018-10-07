<template>
    <nav class="navbar container">
      <div class="navbar-start">
      <router-link class="navbar-item"
                   v-for="routes in links"
                   v-bind:key="routes.id"
                   :to=routes.page>{{routes.text}}</router-link>
      </div>
    <div class="navbar-end">
      <div class="navbar-item">
        <div class="buttons">
          <a class="button is-light">
            Sign up
          </a>
          <a class="button is-light">
            Log in
          </a>
        </div>
      </div>
    </div>
    </nav>
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
          page: '/courses'
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
