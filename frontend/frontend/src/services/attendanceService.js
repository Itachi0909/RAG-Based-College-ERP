import api from "./api";

const attendanceService = {
    // Student attendance
    getMyAttendance: async () => {
        const response = await api.get("/student/attendance");
        return response.data;
    },

    // Teacher attendance sessions
    createSession: async (sessionData) => {
        const response = await api.post(
            "/teacher/attendance/session",
            sessionData
        );
        return response.data;
    },

    getMySessions: async () => {
        const response = await api.get("/teacher/attendance/sessions");
        return response.data;
    },

    getSessionById: async (id) => {
        const response = await api.get(
            `/teacher/attendance/session/${id}`
        );
        return response.data;
    },

    getStudentsForAttendance: async (sessionId) => {
        const response = await api.get(
            `/teacher/attendance/session/${sessionId}/students`
        );
        return response.data;
    },

    markAttendance: async (attendanceData) => {
        const response = await api.post(
            "/teacher/attendance/mark",
            attendanceData
        );
        return response.data;
    },

    updateAttendance: async (attendanceData) => {
        const response = await api.put(
            "/teacher/attendance/mark",
            attendanceData
        );
        return response.data;
    },

    getAttendanceBySession: async (sessionId) => {
        const response = await api.get(
            `/teacher/attendance/session/${sessionId}/attendance`
        );
        return response.data;
    },

    deleteAttendance: async (attendanceId) => {
        const response = await api.delete(
            `/teacher/attendance/${attendanceId}`
        );
        return response.data;
    },
};

export default attendanceService;