import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import Link from "next/link";

export default function SignUp() {
    return (
        <div className="mt-24  rounded bg-black/80 py-10 px-6 md:mt-0 md:max-w-sm md:px-14">
            <form method="POST" action="/users/register">
                <h1 className="text-3xl font-semibold text-white">Sign Up</h1>
                <h1 className="space-y-4 mt-5">
                    <Input
                        type="email"
                        name="email"
                        placeholder="Email"
                        className="bg-[#333] placeholder:text-xs placeholder:text-gray-400 w-full inline-block"
                    />
                    <Input
                        type="password"
                        name="password"
                        placeholder="Password"
                        className="bg-[#333] placeholder:text-xs placeholder:text-gray-400 w-full inline-block"
                    />
                    <Button
                        type="submit"
                        variant="destructive"
                        className="w-full bg-[#e50914]"
                    >
                        Sign Up
                    </Button>
                    
                </h1>
            </form>
            <div className="text-gray-500 text-sm mt-2">
                Alredy Have a account?{" "}
                <Link className="text-white hover:underline" href="/login">
                Log in now!
                </Link>
            </div>
        </div>
    );
}