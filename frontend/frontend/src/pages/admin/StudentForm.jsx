import { useEffect, useState } from "react";
import api from "../../services/api";

function StudentForm({
  initialData = null,
  onSubmit,
  submitButtonText = "Add Student",
}) {
  const [formData, setFormData] = useState({
    // Login Information
    email: "",
    password: "",

    // Personal Information
    firstName: "",
    middleName: "",
    lastName: "",
    gender: "",
    dateOfBirth: "",
    bloodGroup: "",
    photoUrl: "",

    // Contact Information
    phone: "",

    // Academic Information
    enrollmentNumber: "",
    registrationNumber: "",
    rollNumber: "",
    admissionYear: "",
    status: "",

    // Current Address
    currentAddress: "",
    currentCity: "",
    currentState: "",
    currentPincode: "",

    // Permanent Address
    permanentAddress: "",
    permanentCity: "",
    permanentState: "",
    permanentPincode: "",

    // Guardian Information
    guardianName: "",
    guardianPhone: "",
    guardianRelation: "",

    // Academic Relationships
    branchId: "",
    semesterId: "",
  });
  useEffect(() => {
    if (initialData) {
      setFormData({
        ...initialData,
        password: "",
        branchId: initialData.branchId?.toString() || "",
        semesterId: initialData.semesterId?.toString() || "",
      });
    }
  }, [initialData]);

  const [branches, setBranches] = useState([]);
  const [semesters, setSemesters] = useState([]);

  useEffect(() => {
    const fetchAcademicData = async () => {
      try {
        const branchResponse = await api.get("/admin/branches");

        const semesterResponse = await api.get("/admin/semesters");

        setBranches(branchResponse.data);
        setSemesters(semesterResponse.data);
      } catch (error) {
        console.error("Failed to load branches or semesters:", error);
      }
    };

    fetchAcademicData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((previousData) => ({
      ...previousData,
      [name]: value,

      ...(name === "branchId" && {
        semesterId: "",
      }),
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const studentData = {
      email: formData.email,
      password: formData.password,
      firstName: formData.firstName,
      middleName: formData.middleName,
      lastName: formData.lastName,
      gender: formData.gender,
      dateOfBirth: formData.dateOfBirth,
      bloodGroup: formData.bloodGroup,
      photoUrl: formData.photoUrl,
      phone: formData.phone,
      enrollmentNumber: formData.enrollmentNumber,
      registrationNumber: formData.registrationNumber,
      rollNumber: formData.rollNumber,
      admissionYear: Number(formData.admissionYear),
      status: formData.status,
      currentAddress: formData.currentAddress,
      currentCity: formData.currentCity,
      currentState: formData.currentState,
      currentPincode: formData.currentPincode,
      permanentAddress: formData.permanentAddress,
      permanentCity: formData.permanentCity,
      permanentState: formData.permanentState,
      permanentPincode: formData.permanentPincode,
      guardianName: formData.guardianName,
      guardianPhone: formData.guardianPhone,
      guardianRelation: formData.guardianRelation,
      branchId: Number(formData.branchId),
      semesterId: Number(formData.semesterId),
    };

    try {
      if (onSubmit) {
        await onSubmit(studentData);
        return;
      }

      const response = await api.post("/admin/students", studentData);

      console.log("Student created successfully:", response.data);
    } catch (error) {
      console.error(
        "Failed to submit student:",
        error.response?.data || error.message,
      );
    }
  };

  return (
    <>
      <h1 className="text-3xl font-bold">Add Student</h1>

      <p className="mt-2 text-slate-600">Add a new student to the system.</p>

      <form
        onSubmit={handleSubmit}
        className="mt-8 bg-white rounded-lg shadow p-6"
      >
        {/* ==========================
                    Login Information
                ========================== */}

        <div>
          <h2 className="text-xl font-semibold">Login Information</h2>

          <p className="mt-1 text-sm text-slate-500">
            Login credentials for the student.
          </p>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
            <div>
              <label className="block mb-2 font-medium">Email</label>

              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Enter email"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>

            <div>
              <label className="block mb-2 font-medium">Password</label>

              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="Enter password"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required={!initialData}
              />
            </div>
          </div>
        </div>
        {/* ==========================
                 Personal Information
                ========================== */}

        <div className="border-t mt-8 pt-8">
          <h2 className="text-xl font-semibold">Personal Information</h2>

          <p className="mt-1 text-sm text-slate-500">
            Enter the student's personal details.
          </p>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-6">
            {/* First Name */}

            <div>
              <label className="block mb-2 font-medium">First Name</label>

              <input
                type="text"
                name="firstName"
                value={formData.firstName}
                onChange={handleChange}
                placeholder="Enter first name"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>

            {/* Middle Name */}

            <div>
              <label className="block mb-2 font-medium">Middle Name</label>

              <input
                type="text"
                name="middleName"
                value={formData.middleName}
                onChange={handleChange}
                placeholder="Enter middle name"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
              />
            </div>

            {/* Last Name */}

            <div>
              <label className="block mb-2 font-medium">Last Name</label>

              <input
                type="text"
                name="lastName"
                value={formData.lastName}
                onChange={handleChange}
                placeholder="Enter last name"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>

            {/* Gender */}

            <div>
              <label className="block mb-2 font-medium">Gender</label>

              <select
                name="gender"
                value={formData.gender}
                onChange={handleChange}
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              >
                <option value="">Select Gender</option>

                <option value="MALE">Male</option>

                <option value="FEMALE">Female</option>

                <option value="OTHER">Other</option>
              </select>
            </div>

            {/* Date of Birth */}

            <div>
              <label className="block mb-2 font-medium">Date of Birth</label>

              <input
                type="date"
                name="dateOfBirth"
                value={formData.dateOfBirth}
                onChange={handleChange}
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>

            {/* Blood Group */}

            <div>
              <label className="block mb-2 font-medium">Blood Group</label>

              <select
                name="bloodGroup"
                value={formData.bloodGroup}
                onChange={handleChange}
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              >
                <option value="">Select Blood Group</option>

                <option value="A_POSITIVE">A+</option>

                <option value="A_NEGATIVE">A-</option>

                <option value="B_POSITIVE">B+</option>

                <option value="B_NEGATIVE">B-</option>

                <option value="AB_POSITIVE">AB+</option>

                <option value="AB_NEGATIVE">AB-</option>

                <option value="O_POSITIVE">O+</option>

                <option value="O_NEGATIVE">O-</option>
              </select>
            </div>
          </div>

          {/* Photo URL */}

          <div className="mt-6">
            <label className="block mb-2 font-medium">Photo URL</label>

            <input
              type="url"
              name="photoUrl"
              value={formData.photoUrl}
              onChange={handleChange}
              placeholder="Enter photo URL"
              className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
            />
          </div>
        </div>

        {/* ==========================
                Contact Information
                ========================== */}

        <div className="border-t mt-8 pt-8">
          <h2 className="text-xl font-semibold">Contact Information</h2>

          <p className="mt-1 text-sm text-slate-500">
            Enter the student's contact details.
          </p>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
            {/* Phone */}

            <div>
              <label className="block mb-2 font-medium">Phone Number</label>

              <input
                type="tel"
                name="phone"
                value={formData.phone}
                onChange={handleChange}
                placeholder="Enter 10-digit phone number"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>
          </div>
        </div>

        {/* ==========================
                Academic Information
                ========================== */}

        <div className="border-t mt-8 pt-8">
          <h2 className="text-xl font-semibold">Academic Information</h2>

          <p className="mt-1 text-sm text-slate-500">
            Enter the student's academic details.
          </p>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-6">
            {/* Enrollment Number */}

            <div>
              <label className="block mb-2 font-medium">
                Enrollment Number
              </label>

              <input
                type="text"
                name="enrollmentNumber"
                value={formData.enrollmentNumber}
                onChange={handleChange}
                placeholder="Enter enrollment number"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>

            {/* Registration Number */}

            <div>
              <label className="block mb-2 font-medium">
                Registration Number
              </label>

              <input
                type="text"
                name="registrationNumber"
                value={formData.registrationNumber}
                onChange={handleChange}
                placeholder="Enter registration number"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>

            {/* Roll Number */}

            <div>
              <label className="block mb-2 font-medium">Roll Number</label>

              <input
                type="text"
                name="rollNumber"
                value={formData.rollNumber}
                onChange={handleChange}
                placeholder="Enter roll number"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>

            {/* Admission Year */}

            <div>
              <label className="block mb-2 font-medium">Admission Year</label>

              <input
                type="number"
                name="admissionYear"
                value={formData.admissionYear}
                onChange={handleChange}
                placeholder="Enter admission year"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>

            {/* Student Status */}

            <div>
              <label className="block mb-2 font-medium">Student Status</label>

              <select
                name="status"
                value={formData.status}
                onChange={handleChange}
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              >
                <option value="">Select Status</option>

                <option value="ACTIVE">Active</option>

                <option value="INACTIVE">Inactive</option>

                <option value="GRADUATED">Graduated</option>

                <option value="DROPPED">Dropped</option>
              </select>
            </div>
          </div>
        </div>

        {/* ==========================
                Current Address
                ========================== */}

        <div className="border-t mt-8 pt-8">
          <h2 className="text-xl font-semibold">Current Address</h2>

          <p className="mt-1 text-sm text-slate-500">
            Enter the student's current residential address.
          </p>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
            {/* Address */}

            <div className="md:col-span-2">
              <label className="block mb-2 font-medium">Address</label>

              <textarea
                name="currentAddress"
                value={formData.currentAddress}
                onChange={handleChange}
                placeholder="Enter current address"
                rows="3"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
              />
            </div>

            {/* City */}

            <div>
              <label className="block mb-2 font-medium">City</label>

              <input
                type="text"
                name="currentCity"
                value={formData.currentCity}
                onChange={handleChange}
                placeholder="Enter city"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
              />
            </div>

            {/* State */}

            <div>
              <label className="block mb-2 font-medium">State</label>

              <input
                type="text"
                name="currentState"
                value={formData.currentState}
                onChange={handleChange}
                placeholder="Enter state"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
              />
            </div>

            {/* Pincode */}

            <div>
              <label className="block mb-2 font-medium">Pincode</label>

              <input
                type="text"
                name="currentPincode"
                value={formData.currentPincode}
                onChange={handleChange}
                placeholder="Enter pincode"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
              />
            </div>
          </div>
        </div>

        {/* ==========================
                Permanent Address
                ========================== */}

        <div className="border-t mt-8 pt-8">
          <h2 className="text-xl font-semibold">Permanent Address</h2>

          <p className="mt-1 text-sm text-slate-500">
            Enter the student's permanent address.
          </p>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
            {/* Address */}

            <div className="md:col-span-2">
              <label className="block mb-2 font-medium">Address</label>

              <textarea
                name="permanentAddress"
                value={formData.permanentAddress}
                onChange={handleChange}
                placeholder="Enter permanent address"
                rows="3"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
              />
            </div>

            {/* City */}

            <div>
              <label className="block mb-2 font-medium">City</label>

              <input
                type="text"
                name="permanentCity"
                value={formData.permanentCity}
                onChange={handleChange}
                placeholder="Enter city"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
              />
            </div>

            {/* State */}

            <div>
              <label className="block mb-2 font-medium">State</label>

              <input
                type="text"
                name="permanentState"
                value={formData.permanentState}
                onChange={handleChange}
                placeholder="Enter state"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
              />
            </div>

            {/* Pincode */}

            <div>
              <label className="block mb-2 font-medium">Pincode</label>

              <input
                type="text"
                name="permanentPincode"
                value={formData.permanentPincode}
                onChange={handleChange}
                placeholder="Enter pincode"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
              />
            </div>
          </div>
        </div>

        {/* ==========================
                Guardian Information
                ========================== */}

        <div className="border-t mt-8 pt-8">
          <h2 className="text-xl font-semibold">Guardian Information</h2>

          <p className="mt-1 text-sm text-slate-500">
            Enter the student's guardian details.
          </p>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-6">
            {/* Guardian Name */}

            <div>
              <label className="block mb-2 font-medium">Guardian Name</label>

              <input
                type="text"
                name="guardianName"
                value={formData.guardianName}
                onChange={handleChange}
                placeholder="Enter guardian name"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>

            {/* Guardian Phone */}

            <div>
              <label className="block mb-2 font-medium">Guardian Phone</label>

              <input
                type="tel"
                name="guardianPhone"
                value={formData.guardianPhone}
                onChange={handleChange}
                placeholder="Enter guardian phone number"
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              />
            </div>

            {/* Guardian Relation */}

            <div>
              <label className="block mb-2 font-medium">
                Guardian Relation
              </label>

              <select
                name="guardianRelation"
                value={formData.guardianRelation}
                onChange={handleChange}
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              >
                <option value="">Select Relation</option>

                <option value="FATHER">Father</option>

                <option value="MOTHER">Mother</option>

                <option value="BROTHER">Brother</option>

                <option value="SISTER">Sister</option>

                <option value="GUARDIAN">Guardian</option>

                <option value="UNCLE">Uncle</option>

                <option value="AUNT">Aunt</option>

                <option value="OTHER">Other</option>
              </select>
            </div>
          </div>
        </div>

        {/* ==========================
                Branch and Semester
                ========================== */}

        <div className="border-t mt-8 pt-8">
          <h2 className="text-xl font-semibold">Academic Assignment</h2>

          <p className="mt-1 text-sm text-slate-500">
            Select the student's branch and current semester.
          </p>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
            {/* Branch */}

            <div>
              <label className="block mb-2 font-medium">Branch</label>

              <select
                name="branchId"
                value={formData.branchId}
                onChange={handleChange}
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
              >
                <option value="">Select Branch</option>

                {branches.map((branch) => (
                  <option key={branch.id} value={branch.id}>
                    {branch.name}
                  </option>
                ))}
              </select>
            </div>

            {/* Semester */}

            <div>
              <label className="block mb-2 font-medium">Semester</label>

              <select
                name="semesterId"
                value={formData.semesterId}
                onChange={handleChange}
                className="w-full border border-slate-300 rounded-lg px-4 py-2 outline-none focus:ring-2 focus:ring-slate-400"
                required
                disabled={!formData.branchId}
              >
                <option value="">Select Semester</option>

                {semesters
                  .filter(
                    (semester) =>
                      semester.branchId === Number(formData.branchId),
                  )
                  .map((semester) => (
                    <option key={semester.id} value={semester.id}>
                      Semester {semester.semesterNumber}
                    </option>
                  ))}
              </select>
            </div>
          </div>
        </div>

        <div className="border-t mt-8 pt-6 flex justify-end">
          <button
            type="submit"
            className="bg-slate-800 text-white px-6 py-2 rounded-lg hover:bg-slate-700 transition"
          >
            {submitButtonText}
          </button>
        </div>
      </form>
    </>
  );
}

export default StudentForm;
