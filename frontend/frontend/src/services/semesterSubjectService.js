import api from "./api";

const getMySubjects = async () => {
    const response = await api.get("/student/subjects");
    return response.data;
};

export default {
    getMySubjects,
};