import api from "./api";

const login = async (email, password) => {
    const response = await api.post("/auth/login", {
        email,
        password,
    });

    const data = response.data;

    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", data.userId);
    localStorage.setItem("email", data.email);
    localStorage.setItem("role", data.role);

    return data;
};

const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("email");
    localStorage.removeItem("role");
};

const getCurrentUser = () => {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");
    const email = localStorage.getItem("email");
    const role = localStorage.getItem("role");

    if (!token || !role) {
        return null;
    }

    return {
        token,
        userId,
        email,
        role,
    };
};

const isAuthenticated = () => {
    return !!localStorage.getItem("token");
};

export default {
    login,
    logout,
    getCurrentUser,
    isAuthenticated,
};