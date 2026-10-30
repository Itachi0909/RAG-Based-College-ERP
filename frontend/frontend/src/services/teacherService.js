import api from "./api";

const getAllTeachers = async () => {
    const response = await api.get("/admin/teachers");
    return response.data;
};

const getTeacherById = async (id) => {
    const response = await api.get(`/admin/teachers/${id}`);
    return response.data;
};

const createTeacher = async (teacherData) => {
    const response = await api.post("/admin/teachers", teacherData);
    return response.data;
};

const updateTeacher = async (id, teacherData) => {
    const response = await api.put(`/admin/teachers/${id}`, teacherData);
    return response.data;
};

const deleteTeacher = async (id) => {
    const response = await api.delete(`/admin/teachers/${id}`);
    return response.data;
};

const getMyProfile = async () => {
    const response = await api.get("/teacher/profile");
    return response.data;
};

export default {
    getAllTeachers,
    getTeacherById,
    createTeacher,
    updateTeacher,
    deleteTeacher,
    getMyProfile,
};