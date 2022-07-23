import {defineStore} from "pinia";

export const useScheduleStore = defineStore({
    id: "schedule",
    state: () => ({
        schedule: {
            id: null,
            time: '',
            studentId: null,
            studentName: '',
            teacherId: null,
            teacherName: '',
            course: ''
        }
    }),
    getters : {
        getSchedule: (state) => state.schedule
    },
    actions: {
        set(schedule) {
            this.schedule = {...this.schedule, ...schedule}
        },
        delete() {
            this.schedule = {
                id: null,
                time: '',
                studentId: null,
                studentName: '',
                teacherId: null,
                teacherName: '',
                course: ''
            }
        }
    }
})