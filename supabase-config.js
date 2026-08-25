const SUPABASE_URL =
  "https://ylquvyebfgsvipecljuu.supabase.co";

const SUPABASE_ANON_KEY =
  "sb_publishable_Ur8pHDUGsj8yTqo4cnEdmA_hoLwHYxT";

if (
  !window.supabase ||
  typeof window.supabase.createClient !== "function"
) {
  throw new Error(
    "Supabase library failed to load."
  );
}


/*
====================================================
SINGLE SUPABASE CLIENT
====================================================

Every HTML page must load this file after:

<script src="https://cdn.jsdelivr.net/npm/@supabase/supabase-js@2"></script>

Do NOT create another client inside individual
HTML files.

====================================================
*/

const supabaseClient =
  window.supabase.createClient(
    SUPABASE_URL,
    SUPABASE_ANON_KEY,
    {
      auth: {
        persistSession: true,
        autoRefreshToken: true,

        /*
          IMPORTANT:
          We are NOT depending on a magic-link
          redirect for normal password login.
        */
        detectSessionInUrl: true,

        storage:
          window.localStorage,

        flowType: "pkce"
      }
    }
  );


/*
====================================================
GLOBAL ERROR PROTECTION
====================================================
*/

window.addEventListener(
  "unhandledrejection",
  function(event) {

    console.error(
      "Unhandled promise rejection:",
      event.reason
    );

  }
);


/*
====================================================
AUTH STATE DEBUG
====================================================
*/

supabaseClient.auth.onAuthStateChange(
  function(event, session) {

    console.log(
      "Supabase auth:",
      event,
      session
        ? session.user.email
        : "No session"
    );

  }
);
