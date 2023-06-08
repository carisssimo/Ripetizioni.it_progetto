<template>
  <!--NAVBAR PRINCIPALE-->
  <!--<div class="container-fluid">-->
  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">
      <a class="btn-login btn btn-primary" role="button">logout</a>
    </form>
  </nav>
  <!--</div>-->

  {{this.teacherSelected}}
  {{this.subjectSelected}}
  <div class="logged-container title-container container">
    <h1 class="title-main">Ecco qui il resoconto delle tue prenotazioni!</h1>
  </div>

  <!--FORM per selezionare orario e materia -->
  <!--
  <div class="form-container-selector container">
    <form class="form-inline">
      <label class="my-1 mr-2" for="inlineFormCustomSelectCourse">Materia</label>
      <select id="inlineFormCustomSelectCourse" class="custom-select my-1 mr-sm-2" v-model="subjectSelected">
        <option selected>Scegli la materia</option>
        <option v-for="subject in subjects" :key="subject.subjectID" >{{ subject.subjectName }}</option>
      </select>

      <label class="my-1 mr-2" for="inlineFormCustomSelectTime">Professore</label>
      <select id="inlineFormCustomSelectTime" class="custom-select my-1 mr-sm-2" v-model="teacherSelected">
        <option selected>Scegli professore</option>
        <option v-for="teacher in teachers" :key="teacher.name" >{{ teacher.name }} {{ teacher.surname }}
        </option>
      </select>


      <button class="btn-login btn btn-primary my-1" type="submit" >Submit</button>
    </form>
  </div>      -->

  <div class="form-container-selector container">
    <table class="table">
      <thead>
      <tr>
        <th scope="col">Docente</th>
        <th scope="col">Corso</th>
        <th scope="col">Slot Orario</th>
        <th scope="col">Stato</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="av in availabilities" :key="av.id">
        <td>{{ printTeacherName(av.teacherId) }}</td>
        <td>{{ printSubjectName(av.subjectId) }}</td>
        <td>{{ av.dayTime }}</td>
        <td>{{av.state}}</td>
      </tr>
      </tbody>

    </table>
  </div>


</template>

<script>
import {availabilityService} from "@/Service/availabilityService";
import axios from "axios";
import {teacherService} from "@/Service/teachersService";
import {subjectsService} from "@/Service/subjectsService";


export default {
  // eslint-disable-next-line vue/multi-word-component-names

  name: "PersonalPage",
  data: function () {
    return {
      loading: false,
      teachers: {},
      subjects: {},
      availabilities: [],
    }
  },
  created: async function () {
    try {
      this.loading = true;
      let response = await availabilityService.getAvailabilitiesByID();
      this.availabilities=response.data;
      let response1 = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      this.loading = false;
      this.teachers = response1.data;
      this.subjects = response2.data;
      this.availabilities = response.data;
      console.log(this.teachers);
      console.log(this.subjects);
      console.log(this.availabilities);
      console.log(response.data);
    } catch (e) {
      console.log(e);
    }
  },
  methods: {
    printTeacherName(Id){
      for (let i = 0; i < this.teachers.length; i++) {
        if (this.teachers[i].teacherId === Id) {
          console.log(this.teachers[i]);
          return this.teachers[i].name;
        }
      }
      return null;
    },
    printSubjectName(Id){
      for (let i = 0; i < this.subjects.length; i++) {
        if (this.subjects[i].subjectID === Id) {
          console.log(this.subjects[i]);
          return this.subjects[i].subjectName;
        }
      }
      return null;
    },
    booking(id){
      console.log(id)
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'bookingAvailability',
        availabilityId: id,
      };


      axios.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "isBooked") {
              console.log(" prenotato con successo ")
              this.isLogged = true;
            } else {
              alert("prenotazione fallita ");
            }

          })
          .catch(error => {

            console.error(error);
          });



    }

  }
}
</script>

<style scoped>

</style>