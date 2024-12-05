"use client";

import { useState } from "react";

import { Button } from "@/components/ui/button";

export function Terms() {
  const [showExtraTerms, setShowExtraTerms] = useState(false);

  return (
    <div className="text-xs mt-4 mb-10 text-gray-600 max-w-72">
      <div className="mb-5">
        <span>
          This page uses Google reCAPTCHA to verify that you are not a robot
        </span>
        <Button
          variant="ghost"
          onClick={() => setShowExtraTerms(!showExtraTerms)}
          className="opacity-1 text-[#0071eb] hover:bg-transparent p-0 ml-1 h-fit"
        >
          More information
        </Button>
      </div>

      <div className="h-28">
        {showExtraTerms && (
          <p>
            The information collected by Google reCAPTCHA is subject to Googles
            Privacy Policy and Terms of Service and is used to provide, maintain,
            and improve the reCAPTCHA service, as well as for general security
            purposes (Google does not use it for personalized advertising).
          </p>
        )}
      </div>
    </div>
  );
}
