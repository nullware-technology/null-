import Link from "next/link";

import { RegisterForm } from "./RegisterForm";
import { Terms } from "../components/Terms";

export default function RegisterPage() {
  return (
    <div>
      <p className="text-3xl font-bold text-left mb-7">User Registration</p>

      <RegisterForm />

      <div className="mt-4 flex gap-1">
        <p className="text-white opacity-70">Already have an account?</p>
        <Link href="/login" className="opacity-1 text-white">
          Log in here
        </Link>
      </div>

      <Terms />
    </div>
  );
}
