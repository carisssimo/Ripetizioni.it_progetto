<template>
  <!--titolo e descrizione-->
  <div class="title-container container">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <p class="p-start">ripetizioni.it ti aiuta a prenotare ripetizioni con i migliori docenti della tua città </p>
  </div>

  <!--FORM-->
  <div class="form-container container align-items-center">
    <form>
      <div class=" form-group">
        <label for="name">Name</label>
        <input type="text" class="form-group-orange form-control" id="name">

      </div>
      <div class="form-group">
        <label for="surname">Surname</label>
        <input type="text" class="form-group-orange form-control" id="surname">
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input type="password" class="form-group-orange form-control" id="exampleInputPassword1">
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" class="form-group-orange form-control" id="email">
      </div>
      <div class="form-group">
        <label for="exampleFormControlSelect1">Ruolo</label>
        <select class="form-group-orange form-control" id="exampleFormControlSelect1">
          <option>Utente</option>
          <option>Amministratore</option>
        </select>
      </div>

      <a class="btn-login btn btn-primary" href="#" role="button">Registrati</a>
    </form>
  </div>

  <!--bottoni di registrazione e accesso per ospiti-->
  <div class="button-container container align-items-center">



  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Signin"
}
</script>

<style scoped>

</style>