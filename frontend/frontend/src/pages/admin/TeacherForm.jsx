import { useEffect, useState } from "react";
import api from "../../services/api";

function TeacherForm({
  initialData = null,
  onSubmit,
  submitButtonText = "Add Teacher",
}) {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    firstName: "",
    middleName: "",
    lastName: "",
    gender: "",
    dateOfBirth: "",
    photoUrl: "",
    phone: "",
    employeeId: "",
    qualification: "",
    designation: "",
    status: "",
    joiningDate: "",
    departmentId: "",
    branchId: "",
  });

  const [departments, setDepartments] = useState([]);
  const [branches, setBranches] = useState([]);

  useEffect(() => {
    if (initialData) {
      setFormData({
        ...initialData,
        password: "",
        gender: initialData.gender || "",
        designation: initialData.designation || "",
        status: initialData.status || "",
        departmentId: initialData.departmentId?.toString() || "",
        branchId: initialData.branchId?.toString() || "",
      });
    }
  }, [initialData]);

  useEffect(() => {
    const fetchAcademicData = async () => {
      try {
        const departmentResponse = await api.get("/admin/departments");

        const branchResponse = await api.get("/admin/branches");

        setDepartments(departmentResponse.data);
        setBranches(branchResponse.data);
      } catch (error) {
        console.error("Failed to load departments or branches:", error);
      }
    };

    fetchAcademicData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((previousData) => ({
      ...previousData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const teacherData = {
      email: formData.email,
      password: formData.password,
      firstName: formData.firstName,
      middleName: formData.middleName,
      lastName: formData.lastName,
      gender: formData.gender,
      dateOfBirth: formData.dateOfBirth,
      photoUrl: formData.photoUrl,
      phone: formData.phone,
      employeeId: formData.employeeId,
      qualification: formData.qualification,
      designation: formData.designation,
      status: formData.status,
      joiningDate: formData.joiningDate,
      departmentId: Number(formData.departmentId),
      branchId: Number(formData.branchId),
    };

    try {
      if (onSubmit) {
        await onSubmit(teacherData);
        return;
      }

      const response = await api.post("/admin/teachers", teacherData);

      console.log("Teacher created successfully:", response.data);
    } catch (error) {
      console.error("Failed to submit teacher:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white rounded-lg shadow p-6">
      <h2 className="text-xl font-semibold mb-6">Login Information</h2>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div>
          <label className="block text-sm font-medium mb-2">Email</label>

          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            className="w-full border rounded px-3 py-2"
            placeholder="Enter email"
            required
          />
        </div>

        <div>
          <label className="block text-sm font-medium mb-2">Password</label>

          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            className="w-full border rounded px-3 py-2"
            placeholder={
              initialData
                ? "Leave blank to keep current password"
                : "Enter password"
            }
            required={!initialData}
          />
        </div>
      </div>
      {/* Personal Information */}

      <div className="mt-8">
        <h2 className="text-xl font-semibold mb-6">Personal Information</h2>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label className="block text-sm font-medium mb-2">First Name</label>

            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              placeholder="Enter first name"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">
              Middle Name
            </label>

            <input
              type="text"
              name="middleName"
              value={formData.middleName}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              placeholder="Enter middle name"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">Last Name</label>

            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              placeholder="Enter last name"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">Gender</label>

            <select
              name="gender"
              value={formData.gender}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              required
            >
              <option value="">Select Gender</option>
              <option value="MALE">Male</option>
              <option value="FEMALE">Female</option>
              <option value="OTHER">Other</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">
              Date of Birth
            </label>

            <input
              type="date"
              name="dateOfBirth"
              value={formData.dateOfBirth}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">Photo URL</label>

            <input
              type="text"
              name="photoUrl"
              value={formData.photoUrl}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              placeholder="Enter photo URL"
            />
          </div>
        </div>
      </div>
      {/* Contact Information */}

      <div className="mt-8">
        <h2 className="text-xl font-semibold mb-6">Contact Information</h2>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label className="block text-sm font-medium mb-2">Phone</label>

            <input
              type="tel"
              name="phone"
              value={formData.phone}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              placeholder="Enter 10-digit phone number"
              maxLength="10"
              required
            />
          </div>
        </div>
      </div>
      {/* Professional Information */}

      <div className="mt-8">
        <h2 className="text-xl font-semibold mb-6">Professional Information</h2>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label className="block text-sm font-medium mb-2">
              Employee ID
            </label>

            <input
              type="text"
              name="employeeId"
              value={formData.employeeId}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              placeholder="Enter employee ID"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">
              Qualification
            </label>

            <input
              type="text"
              name="qualification"
              value={formData.qualification}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              placeholder="Enter qualification"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">
              Designation
            </label>

            <select
              name="designation"
              value={formData.designation}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              required
            >
              <option value="">Select Designation</option>
              <option value="ASSISTANT_PROFESSOR">Assistant Professor</option>
              <option value="ASSOCIATE_PROFESSOR">Associate Professor</option>
              <option value="PROFESSOR">Professor</option>
              <option value="HOD">HOD</option>
              <option value="GUEST_FACULTY">Guest Faculty</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">Status</label>

            <select
              name="status"
              value={formData.status}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              required
            >
              <option value="">Select Status</option>
              <option value="ACTIVE">Active</option>
              <option value="ON_LEAVE">On Leave</option>
              <option value="RETIRED">Retired</option>
              <option value="RESIGNED">Resigned</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">
              Joining Date
            </label>

            <input
              type="date"
              name="joiningDate"
              value={formData.joiningDate}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              required
            />
          </div>
        </div>
      </div>
      {/* Relationships */}

      <div className="mt-8">
        <h2 className="text-xl font-semibold mb-6">Department & Branch</h2>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label className="block text-sm font-medium mb-2">Department</label>

            <select
              name="departmentId"
              value={formData.departmentId}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
              required
            >
              <option value="">Select Department</option>

              {departments.map((department) => (
                <option key={department.id} value={department.id}>
                  {department.name}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">Branch</label>

            <select
              name="branchId"
              value={formData.branchId}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2"
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
        </div>
      </div>
      <div className="mt-8 flex justify-end">
        <button
          type="submit"
          className="px-6 py-2 bg-slate-700 text-white rounded hover:bg-slate-800"
        >
          {submitButtonText}
        </button>
      </div>
    </form>
  );
}

export default TeacherForm;
