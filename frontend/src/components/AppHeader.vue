<template>
<div>
  <h1 v-html="polonizeRouteName($route.name)"></h1>
</div>
</template>

<script>
export default {
  name: 'AppHeader',
  methods: {
    polonizeRouteName
  }
}
function polonizeRouteName (routeName) {
  switch (routeName) {
    case 'Questions': return 'Pytania'
    case 'Tests': return 'Testy'
    case 'Home': return 'Strona główna'
    case 'Profile': return 'Profil'
    default: return 'Unknown'
  }
}
</script>

<style scoped>

</style>
