<template>
  <!--FORM-->
  <div >
    <div class="form-container container align-items-center">
      <form v-on:submit.prevent="onSubmit">
        <div class=" form-group">
          <label for="exampleInputEmail1">Seleziona una materia </label>
          <input id="email" v-model="email" aria-describedby="emailHelp" class="form-group-orange form-control"
                 required type="email">

        </div>

        <button class="btn-login btn btn-primary" @click="submitForm">Conferma</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "SelectSubject"
}
</script>

<style scoped>

</style>