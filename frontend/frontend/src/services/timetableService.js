import api from "./api";

const timetableService = {
    // Admin methods
    getAllTimetables: async () => {
        const response = await api.get("/admin/timetable");
        return response.data;
    },

    getTimetableById: async (id) => {
        const response = await api.get(`/admin/timetable/${id}`);
        return response.data;
    },

    createTimetable: async (timetableData) => {
        const response = await api.post(
            "/admin/timetable",
            timetableData
        );
        return response.data;
    },

    updateTimetable: async (id, timetableData) => {
        const response = await api.put(
            `/admin/timetable/${id}`,
            timetableData
        );
        return response.data;
    },

    deleteTimetable: async (id) => {
        const response = await api.delete(
            `/admin/timetable/${id}`
        );
        return response.data;
    },

    // Student method
    getStudentTimetable: async () => {
        const response = await api.get("/student/timetable");
        return response.data;
    },

    // Teacher method
    getTeacherTimetable: async () => {
        const response = await api.get("/teacher/timetable");
        return response.data;
    },

    // Admin teacher timetable method
    getAdminTeacherTimetable: async () => {
        const response = await api.get("/admin/timetable/teacher");
        return response.data;
    },

    // Admin semester timetable method
    getTimetableBySemester: async (semesterId) => {
        const response = await api.get(
            `/admin/timetable/semester/${semesterId}`
        );
        return response.data;
    },
};

export default timetableService;