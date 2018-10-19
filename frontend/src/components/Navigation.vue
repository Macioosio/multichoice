<template>
  <div v-if="isVisible">
  <md-tabs md-sync-route class="container" md-alignment="fixed">
    <md-tab id="tab-home" md-label="Home" to="/"></md-tab>
    <md-tab id="tab-pages" md-label="Pytania" to="/courses"></md-tab>
    <md-tab id="tab-posts" md-label="Testy" to="/tests"></md-tab>
    <md-tab id="tab-settings" md-label="Profil" to="/profile"></md-tab>
  </md-tabs>
  </div>
</template>

<script>

export default {
  name: 'Navigation',
  data () {
    return {
      isVisible: true,
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
  },
  methods: {
    setVisibleOnLoad () {
      let route = this.$route.fullPath
      this.isVisible = route !== '/login' && route !== '/register'
    }
  },
  mounted () {
    this.setVisibleOnLoad()
  },
  watch: {
    $route (to, from) {
      let route = this.$route.fullPath
      this.isVisible = route !== '/login' && route !== '/register'
    }
  }
}

</script>

<style scoped>
.spacing{
  Margin-right: 10px
}
</style>
