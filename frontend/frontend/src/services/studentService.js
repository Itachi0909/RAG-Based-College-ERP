import api from "./api";

const getAllStudents = async () => {
    const response = await api.get("/admin/students");

    return response.data;
};

const getStudentById = async (id) => {
    const response = await api.get(
        `/admin/students/${id}`
    );

    return response.data;
};

const createStudent = async (studentData) => {
    const response = await api.post(
        "/admin/students",
        studentData
    );

    return response.data;
};

const updateStudent = async (id, studentData) => {
    const response = await api.put(
        `/admin/students/${id}`,
        studentData
    );

    return response.data;
};

const deleteStudent = async (id) => {
    const response = await api.delete(
        `/admin/students/${id}`
    );

    return response.data;
};

const getMyProfile = async () => {
    const response = await api.get("/student/profile");

    return response.data;
};

export default {
    getAllStudents,
    getStudentById,
    createStudent,
    updateStudent,
    deleteStudent,
    getMyProfile,
};