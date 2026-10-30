import { useEffect, useState } from "react";
import teacherService from "../../services/teacherService";

const TeacherProfile = () => {
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadProfile = async () => {
            try {
                const data = await teacherService.getMyProfile();
                setProfile(data);
            } catch (err) {
                console.error("Failed to load teacher profile:", err);
                setError("Unable to load your profile.");
            } finally {
                setLoading(false);
            }
        };

        loadProfile();
    }, []);

    if (loading) {
        return (
            <div className="min-h-screen bg-slate-50 p-6">
                <div className="rounded-2xl bg-white p-8 text-center shadow-sm">
                    Loading profile...
                </div>
            </div>
        );
    }

    if (error || !profile) {
        return (
            <div className="min-h-screen bg-slate-50 p-6">
                <div className="rounded-2xl bg-white p-8 text-center text-red-600 shadow-sm">
                    {error || "Profile not found."}
                </div>
            </div>
        );
    }

    const fullName = [
        profile.firstName,
        profile.middleName,
        profile.lastName,
    ]
        .filter(Boolean)
        .join(" ");

    const firstLetter = (profile.firstName || "T")
        .charAt(0)
        .toUpperCase();

    return (
        <div className="min-h-screen bg-slate-50 p-6">
            <div className="mb-6">
                <h1 className="text-3xl font-bold text-slate-800">
                    My Profile
                </h1>

                <p className="mt-1 text-slate-500">
                    View your personal and professional information.
                </p>
            </div>

            <div className="overflow-hidden rounded-2xl bg-white shadow-sm">
                <div className="h-32 bg-gradient-to-r from-teal-600 via-cyan-600 to-blue-700" />

                <div className="relative px-6 pb-8 sm:px-8">
                    <div className="-mt-14 flex flex-col gap-5 sm:flex-row sm:items-end">
                        <div className="flex h-28 w-28 items-center justify-center rounded-full border-4 border-white bg-teal-600 text-4xl font-bold text-white shadow-md">
                            {firstLetter}
                        </div>

                        <div className="pb-2">
                            <h2 className="text-2xl font-bold text-slate-800">
                                {fullName || "Teacher"}
                            </h2>

                            <p className="text-sm text-slate-500">
                                {profile.designation || "Faculty Member"}
                            </p>
                        </div>
                    </div>

                    <div className="mt-8 grid gap-6 md:grid-cols-2">
                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Full Name
                            </p>

                            <p className="mt-1 font-semibold text-slate-800">
                                {fullName || "Not available"}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Employee ID
                            </p>

                            <p className="mt-1 font-semibold text-slate-800">
                                {profile.employeeId || "Not available"}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Email Address
                            </p>

                            <p className="mt-1 font-semibold text-slate-800">
                                {profile.email || "Not available"}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Phone Number
                            </p>

                            <p className="mt-1 font-semibold text-slate-800">
                                {profile.phone || "Not available"}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Department
                            </p>

                            <p className="mt-1 font-semibold text-slate-800">
                                {profile.departmentName || "Not assigned"}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Branch
                            </p>

                            <p className="mt-1 font-semibold text-slate-800">
                                {profile.branchName || "Not assigned"}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Qualification
                            </p>

                            <p className="mt-1 font-semibold text-slate-800">
                                {profile.qualification || "Not available"}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Status
                            </p>

                            <p className="mt-1 font-semibold text-slate-800">
                                {profile.status || "Not available"}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default TeacherProfile;