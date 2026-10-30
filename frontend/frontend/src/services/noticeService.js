import api from "./api";

const getAllNotices = async () => {
    const response = await api.get("/admin/notices");
    return response.data;
};

const getNoticeById = async (id) => {
    const response = await api.get(`/admin/notices/${id}`);
    return response.data;
};

const createNotice = async (noticeData) => {
    const response = await api.post("/admin/notices", noticeData);
    return response.data;
};

const updateNotice = async (id, noticeData) => {
    const response = await api.put(`/admin/notices/${id}`, noticeData);
    return response.data;
};

const deleteNotice = async (id) => {
    const response = await api.delete(`/admin/notices/${id}`);
    return response.data;
};

const getStudentNotices = async () => {
    const response = await api.get("/student/notices");
    return response.data;
};

const getTeacherNotices = async () => {
    const response = await api.get("/teacher/notices");
    return response.data;
};


export default {
    getAllNotices,
    getNoticeById,
    createNotice,
    updateNotice,
    deleteNotice,
    getStudentNotices,
    getTeacherNotices,
};