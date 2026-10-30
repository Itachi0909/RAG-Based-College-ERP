import api from "./api";

const subjectService = {
    getAllSubjects: async () => {
        const response = await api.get("/admin/subjects");
        return response.data;
    },

    getSubjectById: async (id) => {
        const response = await api.get(`/admin/subjects/${id}`);
        return response.data;
    },

    createSubject: async (subjectData) => {
        const response = await api.post("/admin/subjects", subjectData);
        return response.data;
    },

    updateSubject: async (id, subjectData) => {
        const response = await api.put(`/admin/subjects/${id}`, subjectData);
        return response.data;
    },

    deleteSubject: async (id) => {
        const response = await api.delete(`/admin/subjects/${id}`);
        return response.data;
    },
};

export default subjectService;