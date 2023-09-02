<template>

  <div class="form-container-selector container">
    <table class="table">
      <thead>
      <tr>
        <th scope="col">Orario</th>
        <th v-for="day in days" :key="day.id" scope="col">{{ day.description }}</th>

      </tr>
      </thead>
      <tbody>
      <tr v-for="slot in slots" :key="slot.id">
        <td>{{ slot.description }}</td>
        <td v-for="day in days" :key="day.id">
          <div v-if="existAvailability(day.id,slot.id)==='true'" class="table_data_slot">
            <ul>
              <li>{{printTeacherName(getProfessor(day.id,slot.id))}}</li>
              <li>{{printSubjectName(getSubject(day.id,slot.id))}}</li>
              <li>{{getState(day.id,slot.id)}}</li>
            </ul>
          </div>
        </td>
      </tr>
      </tbody>

    </table>
  </div>
</template>
<script>
import {availabilityService} from "@/Service/availabilityService";
import {teacherService} from "@/Service/teachersService";
import {subjectsService} from "@/Service/subjectsService";
import {dayService} from "@/Service/dayService";
import {slotService} from "@/Service/slotService";

export default {
  name: 'UserStory',
  data: function(){
    return{
      loading: false,
      teachers: {},
      subjects: {},
      availabilities: [],
      slots: {},
      days: {}
    }
},
  created: async function () {
    try {
      let userId= this.$route.params.userId;
      console.log(userId)
      this.loading = true;
      if(localStorage.getItem("isLogged")==='true' && localStorage.getItem('admin')==='true') {
        let response = await availabilityService.getAvailabilitiesByID2(userId);
        this.availabilities = response.data;
        console.log(response.data);
      }

      let response1 = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      let response3 = await dayService.getAllDays();
      let response4 = await slotService.getAllSlots();
      this.loading = false;
      this.teachers = response1;
      this.subjects = response2;

      this.days = response3;
      this.slots = response4;
      console.log(this.teachers);
      console.log(this.subjects);
      console.log(this.availabilities);
    } catch (e) {
      console.log(e);
    }
  },
  methods: {
    getProfessor(day, slot){
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].teacherId;
        }
      }

    },
    getSubject(day, slot){
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].subjectId;
        }
      }

    },
    getState(day, slot){
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].booking;
        }
      }

    },
    printTeacherName(Id) {
      for (let i = 0; i < this.teachers.length; i++) {
        if (this.teachers[i].teacherId === Id) {
          console.log(this.teachers[i]);
          return this.teachers[i].name;
        }
      }
      return null;
    },
    printSubjectName(Id) {
      for (let i = 0; i < this.subjects.length; i++) {
        if (this.subjects[i].subjectID === Id) {
          console.log(this.subjects[i]);
          return this.subjects[i].subjectName;
        }
      }
      return null;
    },
    printDayDescription(Id) {
      for (let i = 0; i < this.days.length; i++) {
        if (this.days[i].id === Id) {
          console.log(this.days[i]);
          return this.days[i].description;
        }
      }
      return null;
    },

    printSlotDescription(Id) {
      for (let i = 0; i < this.slots.length; i++) {
        if (this.slots[i].id === Id) {
          console.log(this.slots[i]);
          return this.slots[i].description;
        }
      }
      return null;
    },

    createId(dayId,slotId){
      console.log(dayId+" "+slotId)
      return dayId+" "+slotId
    },

    existAvailability(day, slot) {
      for (let i = 0; i<this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {


          console.log(day)
          console.log(slot)

          return 'true'

        }
      }
    },

  }}
</script>
