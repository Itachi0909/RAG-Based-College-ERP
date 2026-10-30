import { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        setError("");
        setLoading(true);

        try {
            const user = await login(email, password);

            if (user.role === "ADMIN") {
                navigate("/admin/dashboard");
            } else if (user.role === "TEACHER") {
                navigate("/teacher/dashboard");
            } else if (user.role === "STUDENT") {
                navigate("/student/dashboard");
            } else {
                setError("Invalid user role.");
            }
        } catch (error) {
            console.error("Login failed:", error);

            setError(
                error?.response?.data?.message ||
                    "Invalid email or password. Please try again."
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-slate-50">
            <div className="flex min-h-screen">

                {/* LEFT SECTION */}
                <div className="relative hidden overflow-hidden bg-gradient-to-br from-teal-100 via-cyan-50 to-sky-100 lg:flex lg:w-1/2">

                    {/* Decorative circles */}
                    <div className="absolute -left-24 -top-24 h-80 w-80 rounded-full bg-teal-300/30 blur-3xl" />
                    <div className="absolute -bottom-32 -right-20 h-96 w-96 rounded-full bg-cyan-300/35 blur-3xl" />

                    <div className="absolute right-20 top-20 h-24 w-24 rounded-full border-[18px] border-teal-200/70" />

                    <div className="relative z-10 flex w-full flex-col justify-center px-16 xl:px-24">

                        {/* Logo */}
                        <div className="mb-8 flex h-16 w-16 items-center justify-center rounded-2xl bg-white text-3xl shadow-lg shadow-teal-100">
                            🎓
                        </div>

                        <p className="mb-3 text-sm font-bold uppercase tracking-[0.3em] text-teal-600">
                            RCET
                        </p>

                        <h1 className="max-w-xl text-5xl font-bold leading-tight text-slate-900">
                            College Management
                            <span className="block text-teal-600">
                                Made Smarter.
                            </span>
                        </h1>

                        <p className="mt-6 max-w-xl text-lg leading-8 text-slate-600">
                            A modern digital platform for managing
                            academics, attendance, subjects, fees,
                            notices and college operations.
                        </p>

                        {/* Feature cards */}
                        <div className="mt-10 grid max-w-xl grid-cols-3 gap-4">

                            <div className="rounded-2xl border border-slate-200 bg-white/80 p-5 shadow-sm backdrop-blur transition hover:-translate-y-1 hover:shadow-md">
                                <div className="mb-3 text-2xl">
                                    👨‍🎓
                                </div>

                                <h3 className="font-semibold text-slate-800">
                                    Students
                                </h3>

                                <p className="mt-1 text-xs leading-5 text-slate-500">
                                    Academic access
                                </p>
                            </div>

                            <div className="rounded-2xl border border-slate-200 bg-white/80 p-5 shadow-sm backdrop-blur transition hover:-translate-y-1 hover:shadow-md">
                                <div className="mb-3 text-2xl">
                                    👨‍🏫
                                </div>

                                <h3 className="font-semibold text-slate-800">
                                    Teachers
                                </h3>

                                <p className="mt-1 text-xs leading-5 text-slate-500">
                                    Teaching tools
                                </p>
                            </div>

                            <div className="rounded-2xl border border-slate-200 bg-white/80 p-5 shadow-sm backdrop-blur transition hover:-translate-y-1 hover:shadow-md">
                                <div className="mb-3 text-2xl">
                                    ⚙️
                                </div>

                                <h3 className="font-semibold text-slate-800">
                                    Admin
                                </h3>

                                <p className="mt-1 text-xs leading-5 text-slate-500">
                                    Complete control
                                </p>
                            </div>

                        </div>

                        {/* Small bottom text */}
                        <div className="mt-10 flex items-center gap-3 text-sm text-slate-500">
                            <span className="h-2 w-2 rounded-full bg-teal-500" />
                            Secure • Simple • Connected
                        </div>
                    </div>
                </div>

                {/* RIGHT SECTION */}
                <div className="flex w-full items-center justify-center bg-white px-5 py-10 sm:px-8 lg:w-1/2">

                    <div className="w-full max-w-md">

                        {/* Mobile branding */}
                        <div className="mb-8 text-center lg:hidden">

                            <div className="mx-auto mb-4 flex h-16 w-16 items-center justify-center rounded-2xl bg-teal-50 text-3xl shadow-sm">
                                🎓
                            </div>

                            <h1 className="text-3xl font-bold text-slate-900">
                                RCET ERP
                            </h1>

                            <p className="mt-1 text-sm text-slate-500">
                                College Management Portal
                            </p>
                        </div>

                        {/* Login card */}
                        <div className="rounded-3xl border border-slate-100 bg-white p-7 shadow-xl shadow-slate-200/60 sm:p-9">

                            {/* Heading */}
                            <div className="mb-8">

                                <div className="mb-3 inline-flex rounded-full bg-teal-50 px-3 py-1 text-xs font-semibold uppercase tracking-wider text-teal-600">
                                    Welcome Back
                                </div>

                                <h2 className="text-3xl font-bold text-slate-900">
                                    Sign in to RCET ERP
                                </h2>

                                <p className="mt-2 text-sm leading-6 text-slate-500">
                                    Enter your credentials to access
                                    your portal.
                                </p>
                            </div>

                            {/* Error */}
                            {error && (
                                <div className="mb-5 rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-600">
                                    {error}
                                </div>
                            )}

                            <form
                                onSubmit={handleSubmit}
                                className="space-y-5"
                            >

                                {/* Email */}
                                <div>
                                    <label className="mb-2 block text-sm font-semibold text-slate-700">
                                        Email Address
                                    </label>

                                    <div className="relative">

                                        <span className="pointer-events-none absolute left-4 top-1/2 -translate-y-1/2 text-lg">
                                            ✉️
                                        </span>

                                        <input
                                            type="email"
                                            value={email}
                                            onChange={(e) =>
                                                setEmail(e.target.value)
                                            }
                                            placeholder="Enter your email"
                                            autoComplete="email"
                                            required
                                            className="w-full rounded-xl border border-slate-200 bg-slate-50 py-3.5 pl-12 pr-4 text-sm text-slate-800 outline-none transition placeholder:text-slate-400 focus:border-teal-500 focus:bg-white focus:ring-4 focus:ring-teal-500/10"
                                        />

                                    </div>
                                </div>

                                {/* Password */}
                                <div>
                                    <label className="mb-2 block text-sm font-semibold text-slate-700">
                                        Password
                                    </label>

                                    <div className="relative">

                                        <span className="pointer-events-none absolute left-4 top-1/2 -translate-y-1/2 text-lg">
                                            🔒
                                        </span>

                                        <input
                                            type={
                                                showPassword
                                                    ? "text"
                                                    : "password"
                                            }
                                            value={password}
                                            onChange={(e) =>
                                                setPassword(e.target.value)
                                            }
                                            placeholder="Enter your password"
                                            autoComplete="current-password"
                                            required
                                            className="w-full rounded-xl border border-slate-200 bg-slate-50 py-3.5 pl-12 pr-16 text-sm text-slate-800 outline-none transition placeholder:text-slate-400 focus:border-teal-500 focus:bg-white focus:ring-4 focus:ring-teal-500/10"
                                        />

                                        <button
                                            type="button"
                                            onClick={() =>
                                                setShowPassword(
                                                    !showPassword
                                                )
                                            }
                                            className="absolute right-3 top-1/2 -translate-y-1/2 rounded-lg px-2 py-1 text-xs font-medium text-slate-500 transition hover:bg-slate-100 hover:text-teal-600"
                                        >
                                            {showPassword
                                                ? "Hide"
                                                : "Show"}
                                        </button>

                                    </div>
                                </div>

                                {/* Login */}
                                <button
                                    type="submit"
                                    disabled={loading}
                                    className="flex w-full items-center justify-center rounded-xl bg-teal-600 px-4 py-3.5 text-sm font-semibold text-white shadow-lg shadow-teal-600/20 transition hover:bg-teal-700 hover:shadow-teal-600/30 focus:outline-none focus:ring-4 focus:ring-teal-500/20 disabled:cursor-not-allowed disabled:opacity-60"
                                >
                                    {loading ? (
                                        <>
                                            <span className="mr-2 h-4 w-4 animate-spin rounded-full border-2 border-white/30 border-t-white" />
                                            Signing in...
                                        </>
                                    ) : (
                                        <>
                                            Sign In
                                            <span className="ml-2">
                                                →
                                            </span>
                                        </>
                                    )}
                                </button>
                            </form>

                            {/* Footer */}
                            <div className="mt-7 border-t border-slate-100 pt-6 text-center">
                                <p className="text-xs font-medium text-slate-500">
                                    RCET ERP Portal
                                </p>

                                <p className="mt-1 text-xs text-slate-400">
                                    Secure access for students,
                                    teachers and administrators
                                </p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LoginPage;