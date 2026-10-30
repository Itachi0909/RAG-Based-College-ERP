import { createContext, useContext, useState } from "react";
import authService from "../services/authService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");
    const email = localStorage.getItem("email");
    const role = localStorage.getItem("role");

    if (token && userId && email && role) {
      return {
        userId,
        email,
        role,
      };
    }

    return null;
  });

  const login = async (email, password) => {
    const data = await authService.login(email, password);

    setUser({
      userId: data.userId,
      email: data.email,
      role: data.role,
    });

    return data;
  };

  const logout = () => {
    authService.logout();
    setUser(null);
  };

  const value = {
    user,
    login,
    logout,
    isAuthenticated: !!user,
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};