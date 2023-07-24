<template>
  <!--FORM-->
  <div >
    <div class="form-container container align-items-center">
      <div class="title-main">1. Seleziona una materia!</div>
      <select v-model="selected">
        <option disabled value="">Seleziona una materia</option>
        <option v-for="subject in subjects" :key="subject.subjectID">{{subject.subjectName}}</option>
      </select>
      <RouterLink :to="'/'+this.selected"><a class="btn-l btn-login btn btn-success" role="button">Avanti</a></RouterLink>
    </div>
  </div>
  {{this.selected}}
</template>



<script>
import {subjectsService} from "@/Service/subjectsService";

export default {
  name: "SelectSubject",
  data() {
    return{
      subjects:{},
      selected:this.selected
    }
  },
  created: async function(){
    try {
      let response = await subjectsService.getAllSubjects();
      this.subjects = response.data;
      console.log(this.subjects);
    }catch (e) {
      console.log(e);
    }
  }
}
</script>

<style scoped>

</style>