import { useEffect, useState } from "react";
import studentService from "../../services/studentService";

const StudentProfile = () => {
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadProfile = async () => {
      try {
        const data = await studentService.getMyProfile();
        setProfile(data);
      } catch (err) {
        console.error("Failed to load profile:", err);
        setError("Unable to load your profile.");
      } finally {
        setLoading(false);
      }
    };

    loadProfile();
  }, []);

  if (loading) {
    return (
      <div className="min-h-screen bg-slate-100 p-6">
        <div className="animate-pulse space-y-6">
          <div className="h-10 w-64 rounded-lg bg-slate-200" />
          <div className="h-64 rounded-2xl bg-white shadow-sm" />
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-slate-100 p-6">
        <div className="rounded-xl border border-red-200 bg-red-50 p-5 text-red-700">
          {error}
        </div>
      </div>
    );
  }

  if (!profile) {
    return null;
  }

  const fullName =
    profile.studentName ||
    profile.name ||
    `${profile.firstName || ""} ${profile.lastName || ""}`.trim() ||
    "Student";

  // Dynamically uses the first letter of every student's name
  const firstLetter = fullName.charAt(0).toUpperCase();

  return (
    <div className="min-h-screen bg-slate-100 p-4 sm:p-6 lg:p-8">
      {/* Page heading */}
      <div className="mb-8">
        <p className="text-sm font-semibold uppercase tracking-wider text-teal-600">
          Student Portal
        </p>

        <h1 className="mt-2 text-3xl font-bold text-slate-800 sm:text-4xl">
          My Profile
        </h1>

        <p className="mt-2 text-slate-500">
          View your personal and academic information.
        </p>
      </div>

      {/* Profile header */}
      <div className="mb-6 overflow-hidden rounded-2xl bg-white shadow-sm">
        <div className="relative h-32 bg-gradient-to-r from-teal-500 via-cyan-600 to-blue-700">
          <div className="absolute -left-10 -top-16 h-48 w-48 rounded-full bg-white/10" />

          <div className="absolute right-10 top-6 h-20 w-20 rounded-full border-8 border-white/10" />
        </div>

        <div className="relative px-6 pb-6 pt-16 sm:px-8">
          <div className="flex flex-col gap-5 sm:flex-row sm:items-center sm:justify-between">
            <div className="flex items-center gap-5">
              {/* Dynamic student avatar */}
              <div className="absolute left-6 top-0 flex h-28 w-28 -translate-y-1/2 items-center justify-center rounded-full border-4 border-white bg-teal-100 text-4xl font-bold text-teal-700 shadow-md sm:left-8">
                {firstLetter}
              </div>

              <div className="ml-32">
                <h2 className="text-2xl font-bold text-slate-800">
                  {fullName}
                </h2>

                <p className="mt-1 text-slate-500">
                  {profile.registrationNumber ||
                    profile.enrollmentNumber ||
                    "Student"}
                </p>
              </div>
            </div>

            <span className="w-fit rounded-full bg-emerald-100 px-4 py-2 text-sm font-semibold text-emerald-700">
              {profile.status || "ACTIVE"}
            </span>
          </div>
        </div>
      </div>

      {/* Information sections */}
      <div className="grid gap-6 lg:grid-cols-2">
        {/* Personal information */}
        <div className="rounded-2xl bg-white p-6 shadow-sm">
          <div className="mb-6 flex items-center gap-3">
            <div className="rounded-xl bg-teal-100 p-3 text-xl">👤</div>

            <div>
              <h2 className="text-xl font-bold text-slate-800">
                Personal Information
              </h2>

              <p className="text-sm text-slate-500">
                Your basic contact details
              </p>
            </div>
          </div>

          <div className="grid gap-5 sm:grid-cols-2">
            <InfoItem label="Email" value={profile.email} />

            <InfoItem label="Phone" value={profile.phone} />

            <InfoItem
              label="Registration Number"
              value={profile.registrationNumber}
            />

            <InfoItem label="Roll Number" value={profile.rollNumber} />
          </div>
        </div>

        {/* Academic information */}
        <div className="rounded-2xl bg-white p-6 shadow-sm">
          <div className="mb-6 flex items-center gap-3">
            <div className="rounded-xl bg-blue-100 p-3 text-xl">🎓</div>

            <div>
              <h2 className="text-xl font-bold text-slate-800">
                Academic Information
              </h2>

              <p className="text-sm text-slate-500">
                Your current academic details
              </p>
            </div>
          </div>

          <div className="grid gap-5 sm:grid-cols-2">
            <InfoItem label="Branch" value={profile.branchName} />

            <InfoItem
              label="Semester"
              value={
                profile.semesterNumber
                  ? `Semester ${profile.semesterNumber}`
                  : profile.semesterName
              }
            />

            <InfoItem label="Admission Year" value={profile.admissionYear} />

            <InfoItem
              label="Student Status"
              value={profile.status || "ACTIVE"}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

const InfoItem = ({ label, value }) => {
  return (
    <div className="rounded-xl border border-slate-200 bg-slate-50 p-4">
      <p className="text-sm font-medium text-slate-500">{label}</p>

      <p className="mt-2 break-words font-semibold text-slate-800">
        {value || "Not available"}
      </p>
    </div>
  );
};

export default StudentProfile;
