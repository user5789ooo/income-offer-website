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
INCOME OFFERS
SUPABASE AUTH CONFIG
====================================================
*/

const supabaseClient =
  window.supabase.createClient(
    SUPABASE_URL,
    SUPABASE_ANON_KEY,
    {
      auth: {

        /*
        Keep the session between page changes.
        */
        persistSession: true,

        /*
        Automatically refresh expired access tokens.
        */
        autoRefreshToken: true,

        /*
        Needed for password-reset links.
        */
        detectSessionInUrl: true,

        /*
        Browser storage.
        */
        storage: window.localStorage,

        /*
        Normal browser auth.
        */
        flowType: "pkce"

      }
    }
  );


/*
====================================================
AUTH DEBUG
====================================================
*/

supabaseClient.auth.onAuthStateChange(
  function(event, session){

    console.log(
      "[INCOME OFFERS AUTH]",
      event,
      session && session.user
        ? session.user.email
        : "NO SESSION"
    );

  }
);


/*
====================================================
GLOBAL ERROR LOG
====================================================
*/

window.addEventListener(
  "unhandledrejection",
  function(event){

    console.error(
      "[INCOME OFFERS] Unhandled rejection:",
      event.reason
    );

  }
);


window.addEventListener(
  "error",
  function(event){

    console.error(
      "[INCOME OFFERS] JavaScript error:",
      event.error || event.message
    );

  }
);
